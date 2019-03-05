package com.belatrixsf.mymovieapp.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.belatrixsf.mymovieapp.R
import com.belatrixsf.mymovieapp.model.entity.Movie
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.belatrixsf.mymovieapp.api.Api.IMAGE_BASE_URL

class MoviesAdapterFragment(private val movies: List<Movie>, private val mContext: Context) :
    RecyclerView.Adapter<MoviesAdapterFragment.MovieViewHolder>() {

    var listenerAdapter: OnItemClickListener ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            MoviesAdapterFragment.MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_card_view, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MoviesAdapterFragment.MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.movie_img_id)

        val myView = itemView.setOnClickListener {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                val movieSelected = movies[position]
                if(listenerAdapter!=null)
                    listenerAdapter!!.getItemMovie(movieSelected)
            }
        }

        fun bind(movie: Movie) {
            Glide.with(itemView)
                .load("$IMAGE_BASE_URL${movie.poster_path}")
                .apply(RequestOptions.placeholderOf(R.color.colorPrimaryDark))
                .into(image)
        }

    }

    interface OnItemClickListener {
        fun getItemMovie(movie: Movie)
    }
}