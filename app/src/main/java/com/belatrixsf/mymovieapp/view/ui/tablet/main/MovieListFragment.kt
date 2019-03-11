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
import com.google.gson.Gson


class MovieListFragment : Fragment(), MoviesAdapterFragment.OnItemClickListener {
    lateinit var movieList: RecyclerView
    lateinit var movieAdapterF: MoviesAdapterFragment
    private var moviesRepository = MoviesRepository.getInstance()
    private var listenerListFragment: OnMovieListListener? = null

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
        movieList = rootView.findViewById(R.id.movie_fragment_recycler)
        movieList.layoutManager = GridLayoutManager(activity, 3) as RecyclerView.LayoutManager?
        showMovies()

    }

    fun showMovies(){
        moviesRepository.getMovies(object : OnGetMoviesCallback {
            override fun onSuccess(movies: List<Movie>) {
                movieAdapterF = MoviesAdapterFragment(movies, view!!.context)
                //connect Fragment - Adapter
                setListener()
                movieList.adapter = movieAdapterF
            }

            override fun onError() {
                Toast.makeText(view!!.context, "Please check your internet connection.", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun showPopularMovies(){
        moviesRepository.getPopularMovies(object : OnGetMoviesCallback {
            override fun onSuccess(movies: List<Movie>) {
                movieAdapterF = MoviesAdapterFragment(movies,view!!.context)
                setListener()
                movieList.adapter = movieAdapterF
            }

            override fun onError() {
                Toast.makeText(view!!.context, "Please check your internet connection.", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun showTopRatedMovies(){
        moviesRepository.getTopRatedMovies(object : OnGetMoviesCallback {
            override fun onSuccess(movies: List<Movie>) {
                movieAdapterF = MoviesAdapterFragment(movies,view!!.context)
                setListener()
                movieList.adapter = movieAdapterF
            }

            override fun onError() {
                Toast.makeText(view!!.context, "Please check your internet connection.", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun showFavorites(){
        val favoritesM = ArrayList<Movie>()
        val sharedPreferences = context!!.getSharedPreferences("movieapppreference", Context.MODE_PRIVATE)
        val allPreferences = sharedPreferences.all
        val item =  allPreferences.entries.iterator()

        while (item.hasNext()){
            val pair = item.next()
            //trae el favorite como json a traves de una llave
            val sMov = sharedPreferences.getString(pair.key, "")
            //convierte el json en object
            val objMovie = Gson().fromJson(sMov, Movie::class.java)
            favoritesM.add(objMovie)
            Log.i("check movie", objMovie.title)
        }
        Log.i("favoritos", allPreferences.size.toString())

        movieAdapterF = MoviesAdapterFragment(favoritesM,view!!.context)
        movieList.adapter = movieAdapterF
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
        movieAdapterF.listenerAdapter = this

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
