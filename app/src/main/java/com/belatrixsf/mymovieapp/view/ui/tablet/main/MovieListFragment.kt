package com.belatrixsf.mymovieapp.view.ui.tablet.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.belatrixsf.mymovieapp.OnGetMoviesCallback
import com.belatrixsf.mymovieapp.R
import com.belatrixsf.mymovieapp.model.entity.Movie
import com.belatrixsf.mymovieapp.repository.MoviesRepository
import com.belatrixsf.mymovieapp.view.adapter.MoviesAdapter
import com.belatrixsf.mymovieapp.view.adapter.MoviesAdapterFragment
import com.belatrixsf.mymovieapp.view.ui.tablet.OnMessageListener


class MovieListFragment : Fragment() {

    lateinit var movieAdapter: MoviesAdapterFragment
    lateinit var moviesRepository: MoviesRepository
    var listener:OnMessageListener? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_movie_list, container, false)
        // Inflate the layout for this fragment
        setupRecycler(rootView)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view!!.setOnClickListener {

        }
        //INTENT TO PASS DATA
    }



    private fun setupRecycler(rootView: View){
        val movieList = rootView.findViewById<RecyclerView>(R.id.movie_fragment_recycler)
        moviesRepository = MoviesRepository.getInstance()
        movieList.layoutManager = GridLayoutManager(activity, 2)
        moviesRepository.getMovies(object :OnGetMoviesCallback{
            override fun onSuccess(movies: List<Movie>) {
                movieAdapter = MoviesAdapterFragment(movies,rootView.context)
                movieList.adapter = movieAdapter
            }
            override fun onError() {
                Toast.makeText(rootView.context,"Please check your internet connection.", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnMessageListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        this.listener = null
    }


}
