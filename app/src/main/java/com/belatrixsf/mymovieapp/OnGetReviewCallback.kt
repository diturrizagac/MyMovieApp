package com.belatrixsf.mymovieapp

import com.belatrixsf.mymovieapp.model.entity.Review

interface OnGetReviewCallback {
    fun onSuccess(reviews: List<Review>)
    fun onError()
}