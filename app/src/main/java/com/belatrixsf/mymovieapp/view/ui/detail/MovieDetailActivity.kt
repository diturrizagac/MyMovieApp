package com.belatrixsf.mymovieapp.view.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.belatrixsf.mymovieapp.R
import com.belatrixsf.mymovieapp.model.entity.MovieEntity
import com.belatrixsf.mymovieapp.view.adapter.MoviesAdapter.Companion.IMAGE_BASE_URL
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MovieDetailActivity : AppCompatActivity() {
    private var titleTv: TextView? = null
    private var descriptionTv: TextView? = null
    private var imageIv: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        titleTv = findViewById(R.id.text_title)
        descriptionTv = findViewById(R.id.text_description)
        imageIv = findViewById(R.id.image_poster_path)

        // Recieve data
        val intent = intent
        val movie = intent.extras!!.getSerializable("movie") as MovieEntity

        // Setting values
        titleTv!!.text = movie.title
        descriptionTv!!.text = movie.overview


        Glide.with(this)
            .load("$IMAGE_BASE_URL${movie.poster_path}")
            .apply(RequestOptions.placeholderOf(R.color.colorPrimaryDark))
            .into(imageIv!!)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
