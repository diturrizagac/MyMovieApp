package com.belatrixsf.mymovieapp.view.ui.tablet

import com.belatrixsf.mymovieapp.model.entity.Movie

interface OnMessageListener {
    fun getAndSendInformationFromAdapter(movie: Movie)
}