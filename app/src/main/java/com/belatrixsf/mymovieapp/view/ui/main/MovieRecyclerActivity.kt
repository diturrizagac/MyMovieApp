package com.belatrixsf.mymovieapp.view.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.belatrixsf.mymovieapp.OnGetMoviesCallback
import com.belatrixsf.mymovieapp.R
import com.belatrixsf.mymovieapp.model.entity.Movie
import com.belatrixsf.mymovieapp.model.entity.MovieEntity
import com.belatrixsf.mymovieapp.repository.MoviesRepository
import com.belatrixsf.mymovieapp.view.adapter.MoviesAdapter

class MovieRecyclerActivity : AppCompatActivity() {
    lateinit var movieList: RecyclerView
    lateinit var movieAdapter: MoviesAdapter
    lateinit var moviesRepository: MoviesRepository
    //lateinit var listMovie: MutableList<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_recycler)
        moviesRepository = MoviesRepository.getInstance()
        movieList = findViewById(R.id.movie_recycler_view)
        movieList.layoutManager = GridLayoutManager(this, 2)

        moviesRepository.getMovies(object : OnGetMoviesCallback {
            override fun onSuccess(movies: List<MovieEntity>) {
                movieAdapter = MoviesAdapter(movies)
                movieList.adapter = movieAdapter
            }

            override fun onError() {
                Toast.makeText(this@MovieRecyclerActivity, "Please check your internet connection.", Toast.LENGTH_SHORT)
                    .show()
            }
        })


//        listMovie = ArrayList()
//        listMovie.add(Movie("The Vegitarian", "Categorie Book", "Description book", R.drawable.thevigitarian))
//        listMovie.add(Movie("The Wild Robot", "Categorie Book", "Description book", R.drawable.thewildrobot))
//        listMovie.add(Movie("Maria Semples", "Categorie Book", "Description book", R.drawable.mariasemples))
//        listMovie.add(Movie("The Martian", "Categorie Book", "Description book", R.drawable.themartian))
//        listMovie.add(Movie("He Died with...", "Categorie Book", "Description book", R.drawable.hediedwith))
//        listMovie.add(Movie("The Vegitarian", "Categorie Book", "Description book", R.drawable.thevigitarian))
//        listMovie.add(Movie("The Wild Robot", "Categorie Book", "Description book", R.drawable.thewildrobot))
//        listMovie.add(Movie("Maria Semples", "Categorie Book", "Description book", R.drawable.mariasemples))
//        listMovie.add(Movie("The Martian", "Categorie Book", "Description book", R.drawable.themartian))
//        listMovie.add(Movie("He Died with...", "Categorie Book", "Description book", R.drawable.hediedwith))
//        listMovie.add(Movie("The Vegitarian", "Categorie Book", "Description book", R.drawable.thevigitarian))
//        listMovie.add(Movie("The Wild Robot", "Categorie Book", "Description book", R.drawable.thewildrobot))
//        listMovie.add(Movie("Maria Semples", "Categorie Book", "Description book", R.drawable.mariasemples))
//        listMovie.add(Movie("The Martian", "Categorie Book", "Description book", R.drawable.themartian))
//        listMovie.add(Movie("He Died with...", "Categorie Book", "Description book", R.drawable.hediedwith))
    }
}
