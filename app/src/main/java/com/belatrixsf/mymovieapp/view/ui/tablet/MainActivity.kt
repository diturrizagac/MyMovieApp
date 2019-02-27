package com.belatrixsf.mymovieapp.view.ui.tablet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.belatrixsf.mymovieapp.FragmentComunicator
import com.belatrixsf.mymovieapp.R
import com.belatrixsf.mymovieapp.model.entity.MovieEntity
import com.belatrixsf.mymovieapp.view.ui.mobile.detail.MovieDetailActivity

class MainActivity : AppCompatActivity(), FragmentComunicator {
    private var isDualPanel = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentMovieDetail = findViewById<View>(R.id.fragment_details)
        isDualPanel = fragmentMovieDetail?.visibility == View.VISIBLE
    }

    override fun displayDetail(movie: MovieEntity) {
        if (isDualPanel) { // If we are in Tablet
            val movieDetailFragment= supportFragmentManager.findFragmentById(R.id.fragment_details) as MovieDetailFragment?
            movieDetailFragment?.displayDetail(movie)
        } else { // When we are in Smart phone
            val intent = Intent(this, MovieDetailActivity::class.java)
            intent.putExtra("movie", movie)
            startActivity(intent)
        }
    }
}
