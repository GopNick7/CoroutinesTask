package com.nekitapp.coroutinestask.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.nekitapp.coroutinestask.R
import com.nekitapp.coroutinestask.data.net.NetManager
import com.nekitapp.coroutinestask.data.net.model.VideoModel
import com.nekitapp.coroutinestask.ui.adapter.MainAdapter
import com.nekitapp.coroutinestask.utils.extensions.visible
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val mainAdapter: MainAdapter by lazy { MainAdapter() }
    private val linearLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(this) }
    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e(TAG, "Caught $exception")
    }

    private val TAG = MainActivity::class.java.simpleName
    private val PAGE_1 = 1
    private val PAGE_2 = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Enable in main thread
        inNeedEnableProgressBar(true)

        GlobalScope.launch(exceptionHandler) {
            // get async responses in Dispatchers.Unconfined
            getResponses(
                getVideo(PAGE_1),
                getVideo(PAGE_2)
            )
        }
    }

    private fun initAdapter() {
        rv_main.apply {
            layoutManager = linearLayoutManager
            this.adapter = mainAdapter
        }
    }

    private fun inNeedEnableProgressBar(isNeedEnable: Boolean) {
        pb_main.visible(isNeedEnable)
    }

    private suspend fun getVideo(offset: Int): VideoModel {
        return NetManager.restApi.getAllVideoAsync(offset).await()
    }

    private fun getResponses(vararg responses: VideoModel) {
        GlobalScope.launch(exceptionHandler) {
            // Disable progress in main thread
            inNeedEnableProgressBar(false)
        }
        initAdapter()

        val internalDataList = responses.map { it.globalData.video.internalDataList }.flatten()
        mainAdapter.showAllItems(internalDataList)

    }
}
