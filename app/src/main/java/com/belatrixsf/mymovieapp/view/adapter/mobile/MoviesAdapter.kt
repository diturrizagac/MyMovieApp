package com.belatrixsf.mymovieapp.view.adapter.mobile

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.belatrixsf.mymovieapp.R
import com.belatrixsf.mymovieapp.api.Api.IMAGE_BASE_URL
import com.belatrixsf.mymovieapp.model.entity.Movie
import com.belatrixsf.mymovieapp.view.ui.mobile.detail.MovieDetailActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class MoviesAdapter(private val movies: List<Movie>, private val mContext: Context) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_card_view, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.movie_img_id) //item_video_cover

        val myView = itemView.setOnClickListener {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                val movieSelected = movies[position]
                val intent = Intent(mContext, MovieDetailActivity::class.java)
                intent.putExtra("movie", movieSelected)
                intent.putExtra("id", movieSelected.id)
                mContext.startActivity(intent)
            }
        }

        fun bind(movie: Movie) {
            Glide.with(itemView)
                .load("$IMAGE_BASE_URL${movie.poster_path}")
                .apply(RequestOptions.placeholderOf(R.color.background900))
                .into(image)
        }


    }


}