package com.belatrixsf.mymovieapp.api

import com.belatrixsf.mymovieapp.model.network.MoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface TmdbApi {
    @GET ("")
    fun getMovies(
        @Query("api_key") apiKey:String
    ): Call<MoviesResponse>

    @GET("discover/movie?sort_by=popularity.desc")
    fun getPopularMovies(
        @Query("api_key") apiKey: String
    ): Call<MoviesResponse>
}