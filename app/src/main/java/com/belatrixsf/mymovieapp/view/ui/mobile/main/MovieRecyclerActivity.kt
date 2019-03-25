package com.belatrixsf.mymovieapp.view.ui.mobile.main

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.belatrixsf.mymovieapp.OnGetItemCallback
import com.belatrixsf.mymovieapp.R
import com.belatrixsf.mymovieapp.data.FavoriteDbHelper
import com.belatrixsf.mymovieapp.model.entity.Movie
import com.belatrixsf.mymovieapp.repository.MoviesRepository
import com.belatrixsf.mymovieapp.view.adapter.mobile.MoviesAdapter
import com.google.gson.Gson

class MovieRecyclerActivity : AppCompatActivity() {
    private lateinit var movieList: RecyclerView
    private var moviesRepository = MoviesRepository.getInstance()
    private var dbHelper = FavoriteDbHelper(this)
    private var flag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_recycler)
        movieList = findViewById(R.id.movie_recycler_view)
        movieList.layoutManager = GridLayoutManager(this, 3)
        showMoviesT()


        movieList.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }

    //flag to refresh favorite list :D
    override fun onResume() {
        super.onResume()
        if (flag){
            showFavoritesSQLite()
            flag = false
        }
    }


    private fun showMoviesT() {
        moviesRepository.getMovies(object : OnGetItemCallback<Movie> {
            override fun onSuccess(items: List<Movie>) {
                setAdapter(
                    MoviesAdapter(items, this@MovieRecyclerActivity)
                )
            }

            override fun onError() {
                showErrorMessage()
            }
        })
    }

    private fun showPopularMovies(){
        moviesRepository.getPopularMovies(object : OnGetItemCallback<Movie> {
            override fun onSuccess(items: List<Movie>) {
                setAdapter(
                    MoviesAdapter(items, this@MovieRecyclerActivity)
                )
            }
            override fun onError() {
                showErrorMessage()
            }
        })
    }

    private fun showTopRatedMovies(){
        moviesRepository.getTopRatedMovies(object : OnGetItemCallback<Movie> {
            override fun onSuccess(items: List<Movie>) {
                setAdapter(
                    MoviesAdapter(items, this@MovieRecyclerActivity)
                )
            }
            override fun onError() {
                showErrorMessage()
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
        setAdapter(MoviesAdapter(favoritesM, this))
    }

    private fun showFavoritesSQLite(){
        val favoritesM = dbHelper.allFavorites
        setAdapter(MoviesAdapter(favoritesM, this))
    }

    fun setAdapter(adapter: MoviesAdapter){
        movieList.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater= menuInflater
        inflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item!!.itemId){
            R.id.option_main -> {
                showMoviesT()
                flag = false
                true
            }
            R.id.option_popular_movies -> {
                showPopularMovies()
                flag = false
                true
            }
            R.id.option_rated_movies -> {
                showTopRatedMovies()
                flag = false
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

    fun showErrorMessage(){
        Toast.makeText(this@MovieRecyclerActivity, "Please check your internet connection.", Toast.LENGTH_SHORT)
            .show()
    }
}
