package com.belatrixsf.mymovieapp.view.ui.tablet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.belatrixsf.mymovieapp.OnGetMoviesCallback
import com.belatrixsf.mymovieapp.R
import com.belatrixsf.mymovieapp.model.entity.MovieEntity
import com.belatrixsf.mymovieapp.repository.MoviesRepository
import com.belatrixsf.mymovieapp.view.adapter.MoviesAdapter


class MovieListFragment : Fragment() {
    //lateinit var movieList: RecyclerView
    lateinit var movieAdapter: MoviesAdapter
    lateinit var moviesRepository: MoviesRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_movie_list, container, false)
        // Inflate the layout for this fragment
        setupRecycler(rootView)
        return rootView
    }


    fun setupRecycler(rootView: View){

        val movieList = rootView.findViewById<RecyclerView>(R.id.movie_fragment_recycler)
        moviesRepository = MoviesRepository.getInstance()
        movieList.layoutManager = GridLayoutManager(activity, 2)
        moviesRepository.getMovies(object :OnGetMoviesCallback{
            override fun onSuccess(movies: List<MovieEntity>) {
                movieAdapter = MoviesAdapter(movies,rootView.context)
                movieList.adapter = movieAdapter
            }

            override fun onError() {
                Toast.makeText(rootView.context,"Please check your internet connection.", Toast.LENGTH_SHORT)
                    .show()
            }
        })

    }
}
