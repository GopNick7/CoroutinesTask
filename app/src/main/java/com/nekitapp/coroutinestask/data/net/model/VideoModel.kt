package com.nekitapp.coroutinestask.data.net.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Nikita R. on 05.04.2019.
 */
data class VideoModel(
    @SerializedName("data") val globalData: GlobalData,
    @SerializedName("status") val status: Boolean
)

data class GlobalData(
    @SerializedName("video") val video: Video
)

data class Video(
    @SerializedName("count") val count: String,
    @SerializedName("data") val internalDataList: List<InternalData>,
    @SerializedName("ip") val ip: List<String>
)

data class InternalData(
    @SerializedName("channel_id") val channelId: String,
    @SerializedName("count_comments") val countComments: String,
    @SerializedName("count_likes") val countLikes: String,
    @SerializedName("count_views") val countViews: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("description") val description: String,
    @SerializedName("device") val device: String,
    @SerializedName("duration") val duration: String,
    @SerializedName("id") val id: String,
    @SerializedName("image") val image: Any,
    @SerializedName("images") val images: List<Any>,
    @SerializedName("isFavorite") val isFavorite: String,
    @SerializedName("isLiked") val isLiked: String,
    @SerializedName("isMobile") val isMobile: String,
    @SerializedName("link") val link: String,
    @SerializedName("public") val `public`: String,
    @SerializedName("record_id") val recordId: String,
    @SerializedName("status") val status: String,
    @SerializedName("title") val title: String,
    @SerializedName("user") val user: User,
    @SerializedName("user_id") val userId: String
)

data class User(
    @SerializedName("email") val email: String,
    @SerializedName("id") val id: String,
    @SerializedName("image") val image: String,
    @SerializedName("status") val status: String,
    @SerializedName("username") val username: String
)