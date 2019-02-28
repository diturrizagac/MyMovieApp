package com.belatrixsf.mymovieapp.repository

import com.belatrixsf.mymovieapp.OnGetVideoCallback
import com.belatrixsf.mymovieapp.api.TmdbApi
import com.belatrixsf.mymovieapp.model.network.VideoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class VideosRepository {
    var mApi: TmdbApi? = null

    constructor(api: TmdbApi) {
        mApi = api
    }

    companion object {
        //const val IMAGE_BASE_URL: String = "https://image.tmdb.org/t/p/w500"
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/"
        const val MY_API_KEY = "533671e2cdaa4f0f3e33fcd54868f671"
        const val LANGUAGE = "en-US"

        var repository: VideosRepository? = null

        fun getInstance(): VideosRepository {
            if (repository == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(YOUTUBE_THUMBNAIL_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                repository = VideosRepository(retrofit.create<TmdbApi>(TmdbApi::class.java))
            }

            return repository as VideosRepository
        }

        fun get(): TmdbApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(YOUTUBE_THUMBNAIL_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create<TmdbApi>(TmdbApi::class.java)
        }
    }

    fun getVideos(id:String, callback: OnGetVideoCallback) {
        val allVideos = get().getVideos(
            MY_API_KEY, id
        )
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