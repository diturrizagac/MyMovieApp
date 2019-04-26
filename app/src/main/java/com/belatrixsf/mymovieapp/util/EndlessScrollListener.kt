package com.belatrixsf.mymovieapp.util

import android.widget.AbsListView

abstract class EndlessScrollListener : AbsListView.OnScrollListener{
    var visibleThreshold = 5
    var currentPage = 0
    var previousTotalItemCount = 0
    var loading = true
    var startingPageIndex = 1

    constructor(visibleThreshold : Int){
        this.visibleThreshold = visibleThreshold
    }

    constructor(visibleThreshold : Int, startPage : Int){
        this.visibleThreshold = visibleThreshold
        this.startingPageIndex = startPage
        this.currentPage = startPage
    }

    override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
        if (totalItemCount < previousTotalItemCount) {
            currentPage = startingPageIndex
            previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                loading = true
            }
        }

        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false
            previousTotalItemCount = totalItemCount
            currentPage++
        }

        if (!loading && (firstVisibleItem + visibleItemCount + visibleThreshold >= totalItemCount)) {
            loading = onLoadMore(currentPage + 1, totalItemCount)
        }
    }

    abstract fun onLoadMore(page: Int, totalItemsCount: Int ) : Boolean

    override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}