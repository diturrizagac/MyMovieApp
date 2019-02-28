package com.belatrixsf.mymovieapp.model.network

import com.belatrixsf.mymovieapp.model.entity.Video
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class VideoResponse {
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @SerializedName("results")
    @Expose
    var results: List<Video>? = null
}