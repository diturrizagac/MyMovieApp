package com.belatrixsf.mymovieapp.view.ui.tablet.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import com.belatrixsf.mymovieapp.R
import com.belatrixsf.mymovieapp.model.entity.Movie
import com.belatrixsf.mymovieapp.view.ui.tablet.OnMessageListener
import com.belatrixsf.mymovieapp.view.ui.tablet.detail.MovieDetailFragment


class MainActivity : AppCompatActivity(), OnMessageListener {
    lateinit var fragmentManager: FragmentManager
    lateinit var movieListFragment: MovieListFragment
    lateinit var movieDetailFragment: MovieDetailFragment

    private var isDualPanel = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentManager = supportFragmentManager
        movieListFragment = fragmentManager.findFragmentById(R.id.fragment_list) as MovieListFragment
        movieDetailFragment = fragmentManager.findFragmentById(R.id.fragment_details) as MovieDetailFragment
        var hola =getString(R.string.islandscape)

        //val fragmentMovieDetail = findViewById<View>(R.id.fragment_details)
        //isDualPanel = movieDetailFragment?.visibility == View.VISIBLE
    }


    override fun getAndSendInformationFromAdapter(movie: Movie) {
        //movieListFragment.bringMovie(movie, )
        movieDetailFragment.displayDetail(movie)
    }

    /*override fun displayDetail(movie: Movie) {
        if (isDualPanel) { // If we are in Tablet
            val movieDetailFragment= supportFragmentManager.findFragmentById(R.id.fragment_details) as MovieDetailFragment?
            movieDetailFragment?.displayDetail(movie)
        } else { // When we are in Smart phone
            val intent = Intent(this, MovieDetailActivity::class.java)
            intent.putExtra("movie", movie)
            startActivity(intent)
        }
    }*/
}
