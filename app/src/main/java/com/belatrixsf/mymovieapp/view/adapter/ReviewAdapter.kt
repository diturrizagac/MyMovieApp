package com.belatrixsf.mymovieapp.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.belatrixsf.mymovieapp.R
import com.belatrixsf.mymovieapp.model.entity.Review

class ReviewAdapter(private val reviews: List<Review>, mContext: Context): RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewAdapter.ReviewViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_review,parent,false)
        return ReviewViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    override fun onBindViewHolder(holder: ReviewAdapter.ReviewViewHolder, position: Int) {
        val review = reviews[position]

    }

    inner class ReviewViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

    }

}