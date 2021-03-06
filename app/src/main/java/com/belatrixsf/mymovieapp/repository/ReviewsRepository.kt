package com.belatrixsf.mymovieapp.repository

import android.util.Log
import com.belatrixsf.mymovieapp.OnGetItemCallback
import com.belatrixsf.mymovieapp.api.Api
import com.belatrixsf.mymovieapp.api.Api.MY_API_KEY
import com.belatrixsf.mymovieapp.api.RestApiAdapter
import com.belatrixsf.mymovieapp.model.entity.Review
import com.belatrixsf.mymovieapp.model.network.ReviewResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewsRepository : Repository{
    var mApiAdapter: RestApiAdapter? = null

    constructor(apiAdapter: RestApiAdapter) {
        mApiAdapter = apiAdapter
    }

    companion object {
        var repository: ReviewsRepository? = null
        fun getInstance(): ReviewsRepository {
            if (repository == null) {
                val retrofit = Api.getRetrofit()
                repository = ReviewsRepository(retrofit.create<RestApiAdapter>(RestApiAdapter::class.java))
            }
            return repository as ReviewsRepository
        }
    }

    fun getReviews(callback: OnGetItemCallback<Review>, id:Int) {
        val allReviews = Api.getRestApiAdapter().getReviews(id,MY_API_KEY)
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