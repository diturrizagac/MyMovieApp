package com.belatrixsf.mymovieapp.view.ui.tablet.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import com.belatrixsf.mymovieapp.R
import com.belatrixsf.mymovieapp.model.entity.Movie
import com.belatrixsf.mymovieapp.view.ui.tablet.detail.MovieDetailFragment


class MainActivity : AppCompatActivity(), MovieListFragment.OnMovieListListener {
    lateinit var fragmentManager: FragmentManager
    lateinit var movieListFragment: MovieListFragment
    lateinit var movieDetailFragment: MovieDetailFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var hola =getString(R.string.islandscape)

        fragmentManager = supportFragmentManager
        movieDetailFragment = fragmentManager.findFragmentById(R.id.fragment_details) as MovieDetailFragment
        movieListFragment = fragmentManager.findFragmentById(R.id.fragment_list) as MovieListFragment
    }

    override fun sendMovie(movie: Movie) {
        movieDetailFragment.displayDetail(movie)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item!!.itemId){
            R.id.option_popular_movies -> {

                true
            }
            R.id.option_rated_movies -> {
                //showTopRatedMovies()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
