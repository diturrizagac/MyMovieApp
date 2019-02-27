package com.belatrixsf.mymovieapp.api

import com.belatrixsf.mymovieapp.model.network.MoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface TmdbApi {
    @GET ("discover/movie?sort_by=popularity.asc")// discover/movie
    fun getMovies(
        @Query("api_key") apiKey:String
    ): Call<MoviesResponse>

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String
    ): Call<MoviesResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String
    ): Call<MoviesResponse>
}