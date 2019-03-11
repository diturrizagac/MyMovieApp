package com.belatrixsf.mymovieapp.repository


import com.belatrixsf.mymovieapp.model.network.MoviesResponse
import com.belatrixsf.mymovieapp.OnGetMoviesCallback
import com.belatrixsf.mymovieapp.api.TmdbApi
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.Call
import retrofit2.Callback


class MoviesRepository {

    var mApi: TmdbApi? = null

    constructor(api: TmdbApi) {
        mApi = api
    }

    companion object {
        //const val IMAGE_BASE_URL: String = "https://image.tmdb.org/t/p/w500"
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val MY_API_KEY = "533671e2cdaa4f0f3e33fcd54868f671"
        const val BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w342"
        const val BASE_BACKDROP_PATH = "https://image.tmdb.org/t/p/w780"
        const val YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v="
        const val YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/"
        const val LANGUAGE = "en-US"

        var repository: MoviesRepository? = null

        fun getInstance(): MoviesRepository {
            if (repository == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                repository = MoviesRepository(retrofit.create<TmdbApi>(TmdbApi::class.java))
            }
            return repository as MoviesRepository
        }

        fun get(): TmdbApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create<TmdbApi>(TmdbApi::class.java)
        }
    }

    fun getMovies(callback: OnGetMoviesCallback) {
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

    fun getPopularMovies(callback: OnGetMoviesCallback){
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

    fun getTopRatedMovies(callback: OnGetMoviesCallback){
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


}

