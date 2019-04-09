package com.nekitapp.coroutinestask.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.nekitapp.coroutinestask.Exeptions.MyException
import com.nekitapp.coroutinestask.Exeptions.MyIOException
import com.nekitapp.coroutinestask.R
import com.nekitapp.coroutinestask.data.net.NetManager
import com.nekitapp.coroutinestask.data.net.model.VideoModel
import com.nekitapp.coroutinestask.ui.adapter.MainAdapter
import com.nekitapp.coroutinestask.utils.extensions.visible
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.io.IOException

/**
 *
1. get 2 pages in parallel
2. display recieved data on UI
3. handle progress
4. handle erros
5. map errors to in app Exceptions
5. do not use retrofit.Response
6. display thumbnails  on UI
7. use suspend functions to wrap data conversion
8. handle component's lifecycle

 */

class MainActivity : AppCompatActivity(), CoroutineScope {

    private val mainAdapter: MainAdapter by lazy { MainAdapter() }
    private val linearLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(this) }

    private val TAG = MainActivity::class.java.simpleName
    private val PAGE_1 = 1
    private val PAGE_2 = 2

    override val coroutineContext =
        Job() + Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            try {
                when (throwable) {
                    IOException() -> {
                        throw MyIOException()
                    }
                    Exception() -> {
                        throw MyException()
                    }
                }
            } catch (ex: java.lang.Exception) {
                Log.e(TAG, "Caught $ex")
            }
            Log.e(TAG, "Caught $throwable")
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initAdapter()

        inNeedEnableProgressBar(true)

        launch {
            // async request(parallel)
            getResponses(
                async { getVideo(PAGE_1) },
                async { getVideo(PAGE_2) }
            )
        }
    }

    private fun initAdapter() {
        rv_main.apply {
            layoutManager = linearLayoutManager
            this.adapter = mainAdapter
        }
    }

    private suspend fun getVideo(offset: Int): VideoModel {
        return NetManager.restApi.getAllVideoAsync(offset).await()
    }

    private suspend fun getResponses(vararg responses: Deferred<VideoModel>) {
        // it.await - combine responses
        val internalDataList = responses.map { it.await().globalData.video.internalDataList }.flatten()

        //switch from IO pool to main thread
        withContext(Dispatchers.Main) {
            mainAdapter.showAllItems(internalDataList)
            inNeedEnableProgressBar(false)
        }
    }

    private fun inNeedEnableProgressBar(isNeedEnable: Boolean) {
        pb_main.visible(isNeedEnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        //release job
        coroutineContext.cancel()
    }
}
