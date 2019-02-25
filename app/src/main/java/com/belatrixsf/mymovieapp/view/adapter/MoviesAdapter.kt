package com.belatrixsf.mymovieapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.belatrixsf.mymovieapp.R
import com.belatrixsf.mymovieapp.model.entity.Movie
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class MoviesAdapter(private val movies: List<Movie>) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    val IMAGE_BASE_URL: String = "http://image.tmdb.org/t/p/w500"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_card_view, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var title: TextView = itemView.findViewById(R.id.movie_title_id)
        private var image: ImageView = itemView.findViewById(R.id.movie_img_id)

        fun bind(movie: Movie) {
            title.text = movie.titleMovie
            Glide.with(itemView)
                .load("$IMAGE_BASE_URL ${movie.thumbnailMovie}")
                .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                .into(image);
        }
    }


}