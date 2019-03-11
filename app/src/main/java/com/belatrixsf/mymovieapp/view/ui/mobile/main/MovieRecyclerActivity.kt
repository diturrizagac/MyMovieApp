package com.belatrixsf.mymovieapp.view.ui.mobile.main

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.belatrixsf.mymovieapp.OnGetMoviesCallback
import com.belatrixsf.mymovieapp.R
import com.belatrixsf.mymovieapp.data.FavoriteContract.FavoriteEntry.COLUMN_ID
import com.belatrixsf.mymovieapp.data.FavoriteContract.FavoriteEntry.COLUMN_OVERVIEW
import com.belatrixsf.mymovieapp.data.FavoriteContract.FavoriteEntry.COLUMN_POSTER_PATH
import com.belatrixsf.mymovieapp.data.FavoriteContract.FavoriteEntry.COLUMN_RATING
import com.belatrixsf.mymovieapp.data.FavoriteContract.FavoriteEntry.COLUMN_RELEASE
import com.belatrixsf.mymovieapp.data.FavoriteContract.FavoriteEntry.COLUMN_TITLE
import com.belatrixsf.mymovieapp.data.FavoriteDbHelper
import com.belatrixsf.mymovieapp.model.entity.Movie
import com.belatrixsf.mymovieapp.repository.MoviesRepository
import com.belatrixsf.mymovieapp.view.adapter.MoviesAdapter
import com.google.gson.Gson

class MovieRecyclerActivity : AppCompatActivity(){
    private lateinit var movieList: RecyclerView
    private var moviesRepository = MoviesRepository.getInstance()
    private var dbHelper = FavoriteDbHelper(this)
    private var flag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_recycler)
        movieList = findViewById(R.id.movie_recycler_view)
        movieList.layoutManager = GridLayoutManager(this, 3)
        showMovies()
    }

    override fun onResume() {
        super.onResume()
        if (flag){
            showFavoritesSQLite()
            flag = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater= menuInflater
        inflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item!!.itemId){
            R.id.option_main -> {
                showMovies()
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
                //showFavorites()
                flag = true
                showFavoritesSQLite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun showMovies(){
        moviesRepository.getMovies(object : OnGetMoviesCallback {
            override fun onSuccess(movies: List<Movie>) {
                setAdapter(MoviesAdapter(movies,this@MovieRecyclerActivity))
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
                setAdapter(MoviesAdapter(movies,this@MovieRecyclerActivity))
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
                setAdapter(MoviesAdapter(movies,this@MovieRecyclerActivity))
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
            //bring favorite as json through key
            val sMov = sharedPreferences.getString(pair.key, "")
            //convert json to object
            val objMovie = Gson().fromJson(sMov, Movie::class.java)
            favoritesM.add(objMovie)
            Log.i("check movie", objMovie.title)
        }
        Log.i("favoritos", allPreferences.size.toString())

        //movieAdapter = MoviesAdapter(favoritesM,this)
        //setAdapter(movieAdapter)
        setAdapter(MoviesAdapter(favoritesM,this))
    }

    private fun showFavoritesSQLite(){
        val favoritesM = dbHelper.allFavorites
        setAdapter(MoviesAdapter(favoritesM,this))
        //movieAdapter = MoviesAdapter(favoritesM,this)
        //setAdapter(movieAdapter)

    }

    fun setAdapter(adapter: MoviesAdapter){
        movieList.adapter = adapter
    }
}
