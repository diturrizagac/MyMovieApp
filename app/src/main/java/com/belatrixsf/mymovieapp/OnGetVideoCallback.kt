package com.belatrixsf.mymovieapp

import com.belatrixsf.mymovieapp.model.entity.Video

interface OnGetVideoCallback {
    fun onSuccess(videos: List<Video>)
    fun onError()
}