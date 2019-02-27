package com.belatrixsf.mymovieapp.view.ui.tablet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.belatrixsf.mymovieapp.R
import com.belatrixsf.mymovieapp.model.entity.MovieEntity
import com.belatrixsf.mymovieapp.view.adapter.MoviesAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_movie_detail.*

class MovieDetailFragment : Fragment() {

    private var titleTv: TextView? = null
    private var descriptionTv: TextView? = null
    private var imageIv: ImageView? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    fun displayDetail(movie: MovieEntity){
        text_title_fragment.text = movie.title
        text_description_fragment.text = movie.overview
        text_duration_fragment.text = movie.popularity.toString()
        Glide.with(this)
            .load("${MoviesAdapter.IMAGE_BASE_URL}${movie.poster_path}")
            .apply(RequestOptions.placeholderOf(R.color.colorPrimaryDark))
            .into(image_poster_path_fragment)
    }
}
