package com.nekitapp.coroutinestask

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getVideo()

    }

    private fun getVideo() {

        NetManager.restApi.getAllVideoAsync(2).onAwait
    }
}
