package com.belatrixsf.mymovieapp.view.ui.mobile.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.belatrixsf.mymovieapp.OnGetMoviesCallback
import com.belatrixsf.mymovieapp.R
import com.belatrixsf.mymovieapp.model.entity.Movie
import com.belatrixsf.mymovieapp.repository.MoviesRepository
import com.belatrixsf.mymovieapp.view.adapter.MoviesAdapter

class MovieRecyclerActivity : AppCompatActivity() {
    lateinit var movieList: RecyclerView
    lateinit var movieAdapter: MoviesAdapter
    var moviesRepository = MoviesRepository.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_recycler)
        //moviesRepository = MoviesRepository.getInstance()
        movieList = findViewById(R.id.movie_recycler_view)
        movieList.layoutManager = GridLayoutManager(this, 2)
        showMovies()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item!!.itemId){
            R.id.option_popular_movies -> {
                showPopularMovies()
                true
            }
            R.id.option_rated_movies -> {
                showTopRatedMovies()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    fun showMovies(){

        moviesRepository.getMovies(object : OnGetMoviesCallback {
            override fun onSuccess(movies: List<Movie>) {
                movieAdapter = MoviesAdapter(movies,this@MovieRecyclerActivity)
                movieList.adapter = movieAdapter
            }
            override fun onError() {
                Toast.makeText(this@MovieRecyclerActivity, "Please check your internet connection.", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun showPopularMovies(){
        moviesRepository.getPopularMovies(object : OnGetMoviesCallback {
            override fun onSuccess(movies: List<Movie>) {
                movieAdapter = MoviesAdapter(movies,this@MovieRecyclerActivity)
                movieList.adapter = movieAdapter
            }

            override fun onError() {
                Toast.makeText(this@MovieRecyclerActivity, "Please check your internet connection.", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun showTopRatedMovies(){
        moviesRepository.getTopRatedMovies(object : OnGetMoviesCallback {
            override fun onSuccess(movies: List<Movie>) {
                movieAdapter = MoviesAdapter(movies,this@MovieRecyclerActivity)
                movieList.adapter = movieAdapter
            }

            override fun onError() {
                Toast.makeText(this@MovieRecyclerActivity, "Please check your internet connection.", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}
