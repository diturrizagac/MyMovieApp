package com.belatrixsf.mymovieapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    const val MY_API_KEY = "533671e2cdaa4f0f3e33fcd54868f671"
    const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w342"
    const val YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/"
    const val YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v="
    const val LANGUAGE = "en-US"
    const val ITEM = 0
    const val LOADING = 1
    //const val IMAGE_BASE_URL: String = "https://image.tmdb.org/t/p/w500"

    fun getRetrofit():Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getRestApiAdapter(): RestApiAdapter {
        return getRetrofit().create<RestApiAdapter>(RestApiAdapter::class.java)
    }

}