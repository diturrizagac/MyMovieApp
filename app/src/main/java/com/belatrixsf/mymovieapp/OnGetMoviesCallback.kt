package com.belatrixsf.mymovieapp

import com.belatrixsf.mymovieapp.model.entity.Movie

interface OnGetMoviesCallback {
    fun onSuccess(movies: List<Movie>)
    fun onError()
}