package com.belatrixsf.mymovieapp

import com.belatrixsf.mymovieapp.model.entity.MovieEntity

interface OnGetMoviesCallback {
    fun onSuccess(movies: List<MovieEntity>)
    fun onError()
}