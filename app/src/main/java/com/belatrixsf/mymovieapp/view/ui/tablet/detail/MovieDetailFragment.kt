package com.belatrixsf.mymovieapp.view.ui.tablet.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.belatrixsf.mymovieapp.R
import com.belatrixsf.mymovieapp.api.Api.IMAGE_BASE_URL
import com.belatrixsf.mymovieapp.model.entity.Movie
import com.belatrixsf.mymovieapp.view.adapter.MoviesAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import kotlinx.android.synthetic.main.layout_detail_body.*
import kotlinx.android.synthetic.main.layout_detail_header.*

class MovieDetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    fun displayDetail(movie: Movie){
        detail_header_title.text = movie.title
        detail_header_release.text = "Release Date : ${movie.release_date}"
        detail_header_star.rating = movie.vote_average /2
        detail_body_overview.text= movie.overview
        Glide.with(this)
            .load("$IMAGE_BASE_URL${movie.backdrop_path}")
            .apply(RequestOptions.placeholderOf(R.color.colorPrimaryDark))
            .into(movie_detail_poster)
    }
}
