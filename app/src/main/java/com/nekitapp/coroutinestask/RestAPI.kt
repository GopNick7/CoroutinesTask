package com.nekitapp.coroutinestask

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Nikita R. on 05.04.2019.
 */
interface RestAPI {

    @GET("api-mobile/video/get-all")
    fun getAllVideoAsync(@Query("offset") offset: Int): Deferred<VideoModel>

}