package com.belatrixsf.mymovieapp.repository

import android.util.Log
import com.belatrixsf.mymovieapp.OnGetItemCallback
import com.belatrixsf.mymovieapp.api.Api.BASE_URL
import com.belatrixsf.mymovieapp.api.Api.MY_API_KEY
import com.belatrixsf.mymovieapp.api.RestApiAdapter
import com.belatrixsf.mymovieapp.model.entity.Video
import com.belatrixsf.mymovieapp.model.network.VideoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class VideosRepository {
    var mApiAdapter: RestApiAdapter? = null

    constructor(apiAdapter: RestApiAdapter) {
        mApiAdapter = apiAdapter
    }

    companion object {

        var repository: VideosRepository? = null

        fun getInstance(): VideosRepository {
            if (repository == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                repository = VideosRepository(retrofit.create<RestApiAdapter>(RestApiAdapter::class.java))
            }

            return repository as VideosRepository
        }

        fun get(): RestApiAdapter {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create<RestApiAdapter>(RestApiAdapter::class.java)
        }
    }

    fun getVideos(callback: OnGetItemCallback<Video>, id: Int) {
        val allVideos = get().getVideos(id,MY_API_KEY)
        Log.i("url videoss",allVideos.request().url().toString())
        allVideos.enqueue(
            object : Callback<VideoResponse> {
                override fun onResponse(call: Call<VideoResponse>, response: Response<VideoResponse>) {
                    if (response.isSuccessful) {
                        val videosResponse = response.body()
                        if (videosResponse?.results != null) {
                            callback.onSuccess(videosResponse.results!!)
                        } else {
                            callback.onError()
                        }
                    } else {
                        callback.onError()
                    }
                }

                override fun onFailure(call: Call<VideoResponse>, t: Throwable) {
                    callback.onError()
                }
            }
        )
    }
}