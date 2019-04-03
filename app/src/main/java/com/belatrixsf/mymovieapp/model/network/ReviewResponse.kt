package com.belatrixsf.mymovieapp.model.network

import com.belatrixsf.mymovieapp.model.entity.Review
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ReviewResponse {
    @SerializedName("id")
    @Expose
    var id: Int = 0
    @SerializedName("page")
    @Expose
    var page: Int = 0

    @SerializedName("results")
    @Expose
    var results: MutableList<Review>?= null

    @SerializedName("total_pages")
    @Expose
    var total_pages: Int = 0

    @SerializedName("total_results")
    @Expose
    var total_results: Int = 0

}