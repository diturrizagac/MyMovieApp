package com.belatrixsf.mymovieapp

import com.belatrixsf.mymovieapp.model.entity.MovieEntity

interface FragmentComunicator {
    fun displayDetail(movie: MovieEntity)
}