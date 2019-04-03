package com.belatrixsf.mymovieapp

interface OnGetItemCallback<D> {
    fun onSuccess(items: MutableList<D>)
    fun onError()
}