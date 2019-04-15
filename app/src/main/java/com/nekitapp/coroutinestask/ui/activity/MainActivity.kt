package com.nekitapp.coroutinestask.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.nekitapp.coroutinestask.R
import com.nekitapp.coroutinestask.ui.adapter.MainAdapter
import com.nekitapp.coroutinestask.utils.extensions.visible
import kotlinx.android.synthetic.main.activity_main.*

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

class MainActivity : AppCompatActivity() {

    private val mainAdapter: MainAdapter by lazy { MainAdapter() }
    private val linearLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(this) }
    private val model: MainViewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }

    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initAdapter()

        inNeedEnableProgressBar(true)

        applyData()
    }

    private fun applyData() {
        model.getResponse()?.observe(this, Observer {
            it?.let {
                mainAdapter.showAllItems(it)
            }
            inNeedEnableProgressBar(false)
        })
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
}
