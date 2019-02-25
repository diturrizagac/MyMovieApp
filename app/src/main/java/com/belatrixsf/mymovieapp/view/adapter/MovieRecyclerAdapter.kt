package com.belatrixsf.mymovieapp.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.belatrixsf.mymovieapp.R
import com.belatrixsf.mymovieapp.model.entity.Movie
import com.belatrixsf.mymovieapp.repository.MoviesRepository
import com.belatrixsf.mymovieapp.view.ui.MovieDetailActivity

class MovieRecyclerAdapter(private val mContext: Context, private val mData: List<Movie>) : RecyclerView.Adapter<MovieRecyclerAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val mInflater = LayoutInflater.from(mContext)
        val view = mInflater.inflate(R.layout.item_movie_card_view, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.tv_movie_title.text = mData[position].titleMovie
        holder.img_movie_thumbail.setImageResource(mData[position].thumbnailMovie!!)
        holder.cardView.setOnClickListener {
            val intent = Intent(mContext, MovieDetailActivity::class.java)

            // passing data to the book activity
            intent.putExtra("Title", mData[position].titleMovie)
            intent.putExtra("Description", mData[position].descriptionMovie)
            intent.putExtra("Thumbnail", mData[position].thumbnailMovie)
            // start the activity

            mContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tv_movie_title: TextView = itemView.findViewById(R.id.movie_title_id)
        var img_movie_thumbail: ImageView = itemView.findViewById(R.id.movie_img_id)
        var cardView: CardView = itemView.findViewById(R.id.cardview_id)

    }
}