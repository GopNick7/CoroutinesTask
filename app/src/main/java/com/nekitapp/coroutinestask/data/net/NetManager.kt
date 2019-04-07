package com.nekitapp.coroutinestask.data.net

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.nekitapp.coroutinestask.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Nikita R. on 05.04.2019.
 */
object NetManager {
    var restApi: RestAPI

    init {
        restApi = Retrofit.Builder().apply {
            baseUrl(BuildConfig.SERVER_URL)
            client(initClient())
            addConverterFactory(GsonConverterFactory.create())
            addCallAdapterFactory(CoroutineCallAdapterFactory())
        }.build().create(RestAPI::class.java)
    }

    private fun initClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder().apply {
            addNetworkInterceptor(interceptor)
            addNetworkInterceptor {
                val originalBuilder = it.request().newBuilder()
                    .apply {
                        addHeader("Content-PresentationsType", "application/json")
                    }
                val request = originalBuilder.build()
                it.proceed(request)
            }
        }.build()
    }
}