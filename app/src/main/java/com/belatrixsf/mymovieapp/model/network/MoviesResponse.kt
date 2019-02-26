package com.belatrixsf.mymovieapp.model.network

import com.belatrixsf.mymovieapp.model.entity.Movie
import com.belatrixsf.mymovieapp.model.entity.MovieEntity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class MoviesResponse {
    @SerializedName("page")
    @Expose
    var page: Int = 0

    @SerializedName("total_results")
    @Expose
    var totalResults: Int = 0

    @SerializedName("results")
    @Expose
    var movies: List<MovieEntity>? = null

    @SerializedName("total_pages")
    @Expose
    var totalPages: Int = 0



}