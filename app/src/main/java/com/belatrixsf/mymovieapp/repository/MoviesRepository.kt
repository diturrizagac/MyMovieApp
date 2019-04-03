package com.belatrixsf.mymovieapp.repository

import android.util.Log
import com.belatrixsf.mymovieapp.OnFinishedListener
import com.belatrixsf.mymovieapp.OnGetItemCallback
import com.belatrixsf.mymovieapp.api.Api
import com.belatrixsf.mymovieapp.api.Api.MY_API_KEY
import com.belatrixsf.mymovieapp.api.RestApiAdapter
import com.belatrixsf.mymovieapp.model.entity.Movie
import com.belatrixsf.mymovieapp.model.network.MoviesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesRepository : Repository{
    var mApiAdapter: RestApiAdapter
    lateinit var onFinishedListener: OnFinishedListener
    private val TAG = "MoviesRepository"

    constructor(apiAdapter: RestApiAdapter) {
        mApiAdapter = apiAdapter
    }

    companion object {
        var repository: MoviesRepository? = null
        fun getInstance(): MoviesRepository {
            if (repository == null) {
                val retrofit = Api.getRetrofit()
                repository = MoviesRepository(retrofit.create<RestApiAdapter>(RestApiAdapter::class.java))
            }
            return repository as MoviesRepository
        }
    }


    fun getMovies(callback: OnGetItemCallback<Movie>, page: Int){
        val allMovies = Api.getRestApiAdapter().getMovies(MY_API_KEY,page)
        requestMovies(allMovies,callback)
    }

    fun getPopularMovies(callback: OnGetItemCallback<Movie>, page: Int){
        val allMovies = Api.getRestApiAdapter().getPopularMovies(MY_API_KEY, page)
        requestMovies(allMovies,callback)
    }

    fun getTopRatedMovies(callback: OnGetItemCallback<Movie>, page: Int){
        val allMovies = Api.getRestApiAdapter().getTopRatedMovies(MY_API_KEY, page)
        requestMovies(allMovies,callback)
    }


    private fun requestMovies(call: Call<MoviesResponse>, callback: OnGetItemCallback<Movie>) {
        call.enqueue(
            object : Callback<MoviesResponse> {
                override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
                    if (response.isSuccessful) {
                        val moviesResponse = response.body()
                        if (moviesResponse?.movies != null) {
                            callback.onSuccess(moviesResponse.movies!!)
                            onFinishedListener.onFinished(moviesResponse.movies!!)
                        } else {
                            callback.onError()
                        }
                    } else {
                        callback.onError()
                    }
                }

                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    callback.onError()
                    Log.e(TAG, t.toString())
                    onFinishedListener.onFailure(t)
                }
            }
        )
    }
}

