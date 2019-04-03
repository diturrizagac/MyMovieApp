package com.belatrixsf.mymovieapp

import com.belatrixsf.mymovieapp.model.entity.Movie

interface OnFinishedListener{
    fun onFinished(movieList : MutableList<Movie>)
    fun onFailure(t : Throwable)
}