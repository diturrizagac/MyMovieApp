package com.belatrixsf.mymovieapp.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.belatrixsf.mymovieapp.R
import com.belatrixsf.mymovieapp.model.entity.MovieEntity
import com.belatrixsf.mymovieapp.view.ui.mobile.detail.MovieDetailActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class MoviesAdapter(private val movies: List<MovieEntity>, private val mContext: Context) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {
    companion object {
        val IMAGE_BASE_URL: String = "https://image.tmdb.org/t/p/w342"

    }
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

        //var title: TextView = itemView.findViewById(R.id.movie_title_id)
        var image: ImageView = itemView.findViewById(R.id.movie_img_id)

        val myView = itemView.setOnClickListener {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                val movieSelected = movies[position]
                val intent = Intent(mContext, MovieDetailActivity::class.java)
                intent.putExtra("movie", movieSelected)
                mContext.startActivity(intent)
            }
        }

        fun bind(movie: MovieEntity) {
            //itemView.setOnClickListener(onClickListener)
            //title.text = movie.title
            Glide.with(itemView)
                .load("${Companion.IMAGE_BASE_URL}${movie.poster_path}")
                .apply(RequestOptions.placeholderOf(R.color.colorPrimaryDark))
                .into(image)
        }


    }


}