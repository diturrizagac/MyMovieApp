package com.belatrixsf.mymovieapp.util

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(private val layoutManager: GridLayoutManager): RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if(!isLoading() && !isLastPage()) {
            if((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                && firstVisibleItemPosition >= 0
                && totalItemCount >= getTotalPageCount()){
                loadMoreItems()
            }
        }
//        layoutManager.let {
//            val visibleItemCount = it.childCount
//            val totalItemCount = it.itemCount
//            val firstVisibleItemPosition = when (layoutManager) {
//                is LinearLayoutManager -> layoutManager.findLastVisibleItemPosition()
//                is GridLayoutManager -> layoutManager.findLastVisibleItemPosition()
//                else -> return
//            }
//
//            if (onLast() || isLoading()) return
//
//            if(endWithAuto) {
//                if(visibleItemCount + firstVisibleItemPosition == totalItemCount) return
//            }
//
//            if ((visibleItemCount + firstVisibleItemPosition + threshold) >= totalItemCount) {
//                loadMore(++currentPage)
//            }
//        }
    }

    abstract  fun loadMoreItems()
    abstract fun getTotalPageCount():Int
    abstract fun isLastPage():Boolean
    abstract fun isLoading():Boolean
}