package com.belatrixsf.mymovieapp.view.adapter.mobile

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.belatrixsf.mymovieapp.R
import com.belatrixsf.mymovieapp.model.entity.Review
import com.ms.square.android.expandabletextview.ExpandableTextView

class ReviewAdapter(private val reviews: List<Review>, mContext: Context): RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_review,parent,false)
        return ReviewViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]
        holder.bindReview(review)
    }

    inner class ReviewViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var titleReview: TextView = itemView.findViewById(R.id.item_review_title)
        var expandTextReview: ExpandableTextView = itemView.findViewById(R.id.item_review_content)

        fun bindReview(review:Review) {
            Log.i("reviewwwwww","")
            titleReview.text = review.author
            expandTextReview.text = review.content
        }
    }
}