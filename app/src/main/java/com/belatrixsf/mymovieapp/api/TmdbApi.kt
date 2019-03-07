package com.belatrixsf.mymovieapp.api

import com.belatrixsf.mymovieapp.model.network.MoviesResponse
import com.belatrixsf.mymovieapp.model.network.ReviewResponse
import com.belatrixsf.mymovieapp.model.network.VideoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface TmdbApi {
    @GET("discover/movie?sort_by=popularity.asc")// discover/movie
    fun getMovies(
        @Query("api_key") apiKey: String
    ): Call<MoviesResponse>

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String
    ): Call<MoviesResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String
    ): Call<MoviesResponse>

    @GET("movie/{movie_id}/videos")
    fun getVideos(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<VideoResponse>

    @GET("movie/{movie_id}/reviews")
    fun getReviews(
        @Path("movie_id") movieId:Int,
        @Query("api_key") apiKey: String
    ): Call<ReviewResponse>

}