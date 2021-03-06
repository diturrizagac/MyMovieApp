package com.belatrixsf.mymovieapp.view.ui.mobile.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.belatrixsf.mymovieapp.OnGetItemCallback
import com.belatrixsf.mymovieapp.R
import com.belatrixsf.mymovieapp.data.FavoriteDbHelper
import com.belatrixsf.mymovieapp.model.entity.Movie
import com.belatrixsf.mymovieapp.repository.MoviesRepository
import com.belatrixsf.mymovieapp.util.EndlessRecyclerViewScrollListener
import com.belatrixsf.mymovieapp.util.Messages
import com.belatrixsf.mymovieapp.view.adapter.mobile.MoviesAdapter
import com.google.gson.Gson

class MovieRecyclerActivity : AppCompatActivity() {
    private lateinit var movieList: RecyclerView
    private var moviesRepository = MoviesRepository.getInstance()
    private var dbHelper = FavoriteDbHelper(this)
    private var flag = false
    private var currentPage = 0
    private val context = this@MovieRecyclerActivity
    private var layoutManager = GridLayoutManager(this, 3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_recycler)
        initializeUI()
        //showMoviesT()
    }

    private fun initializeUI(){
        movieList = findViewById(R.id.movie_recycler_view)
        movieList.layoutManager = layoutManager
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
            override fun onSuccess(items: MutableList<Movie>) {
                setAdapter(
                    MoviesAdapter(items, context)
                )
            }

            override fun onError() {
                Messages().showErrorMessage(context)
            }
        },currentPage)
    }

    private fun showPopularMovies(){
        moviesRepository.getPopularMovies(object : OnGetItemCallback<Movie> {
            override fun onSuccess(items: MutableList<Movie>) {
                setAdapter(
                    MoviesAdapter(items, context)
                )
            }
            override fun onError() {
                Messages().showErrorMessage(context)
            }
        },currentPage)
    }

    private fun showTopRatedMovies(){
        moviesRepository.getTopRatedMovies(object : OnGetItemCallback<Movie> {
            override fun onSuccess(items: MutableList<Movie>) {
                setAdapter(
                    MoviesAdapter(items, context)
                )
            }
            override fun onError() {
                Messages().showErrorMessage(context)
            }
        },currentPage)
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
                currentPage = 1
                flag = false
                true
            }
            R.id.option_popular_movies -> {
                showPopularMovies()
                currentPage = 1
                flag = false
                true
            }
            R.id.option_rated_movies -> {
                showTopRatedMovies()
                currentPage = 1
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
}
