package com.belatrixsf.mymovieapp

interface OnGetItemCallback<D> {
    fun onSuccess(items: List<D>)
    fun onError()
}