package com.belatrixsf.mymovieapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.belatrixsf.mymovieapp.R
import com.belatrixsf.mymovieapp.model.entity.Movie
import com.belatrixsf.mymovieapp.model.entity.MovieEntity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class MoviesAdapter(private val movies: List<MovieEntity>) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    val IMAGE_BASE_URL: String = "https://image.tmdb.org/t/p/w342"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_card_view, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
        //IMPLEMENT DETAILS HERE WITH INTENT !!!!!!!! XD
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var title: TextView = itemView.findViewById(R.id.movie_title_id)
        var image: ImageView = itemView.findViewById(R.id.movie_img_id)

        fun bind(movie: MovieEntity) {
            title.text = movie.title
            Glide.with(itemView)
                .load("$IMAGE_BASE_URL${movie.poster_path}")
                .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                .into(image);
        }
    }


}