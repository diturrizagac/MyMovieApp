package com.belatrixsf.mymovieapp.repository

import com.belatrixsf.mymovieapp.OnGetReviewCallback
import com.belatrixsf.mymovieapp.api.TmdbApi
import com.belatrixsf.mymovieapp.model.network.MoviesResponse
import com.belatrixsf.mymovieapp.model.network.ReviewResponse
import com.belatrixsf.mymovieapp.view.ui.mobile.detail.MovieDetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ReviewsRepository {
    var mApi: TmdbApi? = null


    constructor(api: TmdbApi) {
        mApi = api
    }

    companion object {
        const val YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v="
        const val YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/"
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val MY_API_KEY = "533671e2cdaa4f0f3e33fcd54868f671"
        var repository: ReviewsRepository? = null

        //singleton pattern
        fun getInstance(): ReviewsRepository {
            if (ReviewsRepository.repository == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(ReviewsRepository.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                ReviewsRepository.repository = ReviewsRepository(retrofit.create<TmdbApi>(TmdbApi::class.java))
            }

            return ReviewsRepository.repository as ReviewsRepository
        }

        fun get(): TmdbApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(ReviewsRepository.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create<TmdbApi>(TmdbApi::class.java)
        }
    }

    fun getReviews(callback: OnGetReviewCallback, id:String) {
        val allReviews = ReviewsRepository.get().getReviews(
            1
        )
        allReviews.enqueue(
            object : Callback<ReviewResponse> {
                override fun onResponse(call: Call<ReviewResponse>, response: Response<ReviewResponse>) {
                    if (response.isSuccessful) {
                        val reviewResponse = response.body()
                        if (reviewResponse?.results != null) {
                            callback.onSuccess(reviewResponse.results!!)
                        } else {
                            callback.onError()
                        }
                    } else {
                        callback.onError()
                    }
                }

                override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                    callback.onError()
                }
            }
        )
    }
}