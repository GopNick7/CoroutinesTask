package com.nekitapp.coroutinestask.ui.activity

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.nekitapp.coroutinestask.data.net.NetManager
import com.nekitapp.coroutinestask.data.net.model.InternalData
import com.nekitapp.coroutinestask.data.net.model.VideoModel
import kotlinx.coroutines.*

/**
 * Created by Nikita R. on 15.04.2019.
 */
class MainViewModel : ViewModel(), CoroutineScope {


    override val coroutineContext = Job() + Dispatchers.Main + CoroutineExceptionHandler { _, throwable ->
        Log.e("MainViewModel", "Caught $throwable")
    }

    private val liveData = MutableLiveData<List<InternalData>>()

    private val PAGE_1 = 1
    private val PAGE_2 = 2

    fun getResponse(): LiveData<List<InternalData>>? {

        Log.e("TAG", liveData.value.toString())
        launch {
            liveData.postValue(zipResponses(
                async { getVideo(PAGE_1) },
                async { getVideo(PAGE_2) }
            )
            )
        }
        return liveData
    }

    private suspend fun zipResponses(vararg responses: Deferred<VideoModel>): List<InternalData> {
        // it.await - combine responses
        return responses.map {
            it.await().globalData.video.internalDataList
        }.flatten()
    }

    private suspend fun getVideo(offset: Int): VideoModel {
        return NetManager.restApi.getAllVideoAsync(offset).await()
    }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancel()
    }

}