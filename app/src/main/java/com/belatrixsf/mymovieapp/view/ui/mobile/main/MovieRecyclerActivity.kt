package com.belatrixsf.mymovieapp.view.ui.mobile.main

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import com.google.gson.Gson

class MovieRecyclerActivity : AppCompatActivity(){
    lateinit var movieList: RecyclerView
    lateinit var movieAdapter: MoviesAdapter
    var moviesRepository = MoviesRepository.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_recycler)
        movieList = findViewById(R.id.movie_recycler_view)
        movieList.layoutManager = GridLayoutManager(this, 3)
        showMovies()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item!!.itemId){
            R.id.option_main -> {
                showPopularMovies()
                true
            }
            R.id.option_popular_movies -> {
                showPopularMovies()
                true
            }
            R.id.option_rated_movies -> {
                showTopRatedMovies()
                true
            }
            R.id.option_favorites -> {
                showFavorites()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun showMovies(){
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

    private fun showPopularMovies(){
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

    private fun showTopRatedMovies(){
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

    private fun showFavorites(){
        val favoritesM = ArrayList<Movie>()
        val sharedPreferences = getSharedPreferences("movieapppreference", Context.MODE_PRIVATE)
        val allPreferences = sharedPreferences.all
        val item =  allPreferences.entries.iterator()

        while (item.hasNext()){
            val pair = item.next()
            //trae el favorite como json a traves de una llave
            val sMov = sharedPreferences.getString(pair.key, "")
            //convierte el json en objeto
            val objMovie = Gson().fromJson(sMov, Movie::class.java)
            favoritesM.add(objMovie)
            Log.i("check movie", objMovie.title)
        }
        Log.i("favoritos", allPreferences.size.toString())

        movieAdapter = MoviesAdapter(favoritesM,this)
        movieList.adapter = movieAdapter
    }
}
