package com.belatrixsf.mymovieapp.repository

import android.util.Log
import com.belatrixsf.mymovieapp.OnGetReviewCallback
import com.belatrixsf.mymovieapp.api.Api.BASE_URL
import com.belatrixsf.mymovieapp.api.Api.MY_API_KEY
import com.belatrixsf.mymovieapp.api.TmdbApi
import com.belatrixsf.mymovieapp.model.network.ReviewResponse
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
        var repository: ReviewsRepository? = null

        //singleton pattern
        fun getInstance(): ReviewsRepository {
            if (repository == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                repository = ReviewsRepository(retrofit.create<TmdbApi>(TmdbApi::class.java))
            }

            return repository as ReviewsRepository
        }

        fun get(): TmdbApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create<TmdbApi>(TmdbApi::class.java)
        }
    }

    fun getReviews(callback: OnGetReviewCallback, id:Int) {
        val allReviews = get().getReviews(id,MY_API_KEY)
        Log.i("url reviews",allReviews.request().url().toString())
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