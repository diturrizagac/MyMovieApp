package com.belatrixsf.mymovieapp.repository


import com.belatrixsf.mymovieapp.OnGetItemCallback
import com.belatrixsf.mymovieapp.model.network.MoviesResponse
import com.belatrixsf.mymovieapp.OnGetMoviesCallback
import com.belatrixsf.mymovieapp.api.RestApiAdapter
import com.belatrixsf.mymovieapp.model.entity.Movie
import com.belatrixsf.mymovieapp.api.Api.MY_API_KEY
import com.belatrixsf.mymovieapp.api.Api.BASE_URL
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.Call
import retrofit2.Callback


class MoviesRepository {

    var mApiAdapter: RestApiAdapter

    constructor(apiAdapter: RestApiAdapter) {
        mApiAdapter = apiAdapter
    }

    companion object {
        var repository: MoviesRepository? = null

        fun getInstance(): MoviesRepository {
            if (repository == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                repository = MoviesRepository(retrofit.create<RestApiAdapter>(RestApiAdapter::class.java))
            }
            return repository as MoviesRepository
        }

        fun get(): RestApiAdapter {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create<RestApiAdapter>(RestApiAdapter::class.java)
        }
    }


    fun getMovies(callback: OnGetItemCallback<Movie>){
        val allMovies = get().getMovies(MY_API_KEY)
        requestMovies(allMovies,callback)
    }

    fun getPopularMovies(callback: OnGetItemCallback<Movie>){
        val allMovies = get().getPopularMovies(MY_API_KEY)
        requestMovies(allMovies,callback)
    }

    fun getTopRatedMovies(callback: OnGetItemCallback<Movie>){
        val allMovies = get().getTopRatedMovies(MY_API_KEY)
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
                        } else {
                            callback.onError()
                        }
                    } else {
                        callback.onError()
                    }
                }

                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    callback.onError()
                }
            }
        )
    }



    @Deprecated("this method is deprecated use getMovies")
    private fun getMovies(callback: OnGetMoviesCallback) {
        val allMovies = get().getMovies(MY_API_KEY)
        allMovies.enqueue(
            object : Callback<MoviesResponse> {

                override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
                    if (response.isSuccessful) {
                        val moviesResponse = response.body()
                        if (moviesResponse?.movies != null) {
                            callback.onSuccess(moviesResponse.movies!!)
                        } else {
                            callback.onError()
                        }
                    } else {
                        callback.onError()
                    }
                }

                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    callback.onError()
                }
            }
        )

    }

    @Deprecated("this method is deprecated use getPopularMovies")
    private fun getPopularMovies(callback: OnGetMoviesCallback){
        val popularMovies = get().getPopularMovies(MY_API_KEY)
        popularMovies.enqueue(
            object : Callback<MoviesResponse> {

                override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
                    if (response.isSuccessful) {
                        val moviesResponse = response.body()
                        if (moviesResponse?.movies != null) {
                            callback.onSuccess(moviesResponse.movies!!)
                        } else {
                            callback.onError()
                        }
                    } else {
                        callback.onError()
                    }
                }

                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    callback.onError()
                }
            }
        )
    }
    @Deprecated("this method is deprecated use getTopRatedMovies")
    private fun getTopRatedMovies(callback: OnGetMoviesCallback){
        val popularMovies = get().getTopRatedMovies(MY_API_KEY)
        popularMovies.enqueue(
            object : Callback<MoviesResponse> {
                override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
                    if (response.isSuccessful) {
                        val moviesResponse = response.body()
                        if (moviesResponse?.movies != null) {
                            callback.onSuccess(moviesResponse.movies!!)
                        } else {
                            callback.onError()
                        }
                    } else {
                        callback.onError()
                    }
                }

                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    callback.onError()
                }
            }
        )
    }
// hacer una plantilla, se repite es el mismo comportamiento
}

