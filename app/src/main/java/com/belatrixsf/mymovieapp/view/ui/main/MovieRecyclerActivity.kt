package com.belatrixsf.mymovieapp.view.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.belatrixsf.mymovieapp.OnGetMoviesCallback
import com.belatrixsf.mymovieapp.R
import com.belatrixsf.mymovieapp.model.entity.MovieEntity
import com.belatrixsf.mymovieapp.repository.MoviesRepository
import com.belatrixsf.mymovieapp.view.adapter.MoviesAdapter

class MovieRecyclerActivity : AppCompatActivity() {
    lateinit var movieList: RecyclerView
    lateinit var movieAdapter: MoviesAdapter
    lateinit var moviesRepository: MoviesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_recycler)
        moviesRepository = MoviesRepository.getInstance()
        movieList = findViewById(R.id.movie_recycler_view)
        movieList.layoutManager = GridLayoutManager(this, 2)

        moviesRepository.getMovies(object : OnGetMoviesCallback {
            override fun onSuccess(movies: List<MovieEntity>) {
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
