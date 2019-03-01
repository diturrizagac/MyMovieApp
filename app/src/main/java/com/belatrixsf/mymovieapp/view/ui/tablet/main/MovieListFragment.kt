package com.belatrixsf.mymovieapp.view.ui.tablet.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.belatrixsf.mymovieapp.OnGetMoviesCallback
import com.belatrixsf.mymovieapp.R
import com.belatrixsf.mymovieapp.model.entity.Movie
import com.belatrixsf.mymovieapp.repository.MoviesRepository
import com.belatrixsf.mymovieapp.view.adapter.MoviesAdapterFragment


class MovieListFragment : Fragment(), MoviesAdapterFragment.OnItemClickListener {
    lateinit var movieAdapter: MoviesAdapterFragment
    lateinit var moviesRepository: MoviesRepository
    var listenerListFragment: OnMovieListListener? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_movie_list, container, false)
        // Inflate the layout for this fragment
        setupRecycler(rootView)
        return rootView
    }

    private fun setupRecycler(rootView: View) {
        val movieList = rootView.findViewById<RecyclerView>(R.id.movie_fragment_recycler)
        moviesRepository = MoviesRepository.getInstance()
        movieList.layoutManager = GridLayoutManager(activity, 2)

        moviesRepository.getMovies(object : OnGetMoviesCallback {
            override fun onSuccess(movies: List<Movie>) {
                movieAdapter = MoviesAdapterFragment(movies, rootView.context)
                //connect Frsgm - Adapter
                setListener()
                movieList.adapter = movieAdapter
            }

            override fun onError() {
                Toast.makeText(rootView.context, "Please check your internet connection.", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    //CALLBACK TO BRING DATA FROM ADAPTER
    override fun getItemMovie(movie: Movie) {
        Log.d("Movie", movie.original_language)
        //call listenerAdapter activity
        if (listenerListFragment != null) {
            listenerListFragment!!.sendMovie(movie)
        }
    }

    fun setListener() {
        movieAdapter.listenerAdapter = this

    }

    override fun onAttach(context: Context) { //connect with activity
        super.onAttach(context)
        if (context is OnMovieListListener) {
            listenerListFragment = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() { // disconnect with activity
        super.onDetach()
        this.listenerListFragment = null
    }

    interface OnMovieListListener{
        fun sendMovie(movie: Movie)
    }
}
