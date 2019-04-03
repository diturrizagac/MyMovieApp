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
import com.belatrixsf.mymovieapp.OnGetItemCallback
import com.belatrixsf.mymovieapp.OnGetMoviesCallback
import com.belatrixsf.mymovieapp.R
import com.belatrixsf.mymovieapp.data.FavoriteDbHelper
import com.belatrixsf.mymovieapp.model.entity.Movie
import com.belatrixsf.mymovieapp.repository.MoviesRepository
import com.belatrixsf.mymovieapp.util.Messages
import com.belatrixsf.mymovieapp.view.adapter.mobile.MoviesAdapter
import com.belatrixsf.mymovieapp.view.adapter.tablet.MoviesAdapterFragment
import com.belatrixsf.mymovieapp.view.ui.tablet.detail.MovieDetailFragment
import com.google.gson.Gson


class MovieListFragment : Fragment(), MoviesAdapterFragment.OnItemClickListener {
    private lateinit var movieList: RecyclerView
    private lateinit var dbHelper :FavoriteDbHelper
    private var moviesRepository = MoviesRepository.getInstance()
    private var listenerListFragment: OnMovieListListener? = null
    private var currentPage = 1
    private val callback = object : OnGetItemCallback<Movie>{
        override fun onSuccess(items: MutableList<Movie>) {
            //connect Fragment - Adapter
            setAdapter(MoviesAdapterFragment(items, view!!.context))
            listenerListFragment!!.sendMovie(items[0])
        }

        override fun onError() {
            Messages().showErrorMessage(view!!.context,"Please check your internet connection.")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dbHelper = FavoriteDbHelper(this.context!!)
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
        moviesRepository.getMovies(callback,currentPage)
    }

    fun showPopularMovies(){
        moviesRepository.getPopularMovies(callback,currentPage)
    }

    fun showTopRatedMovies(){
        moviesRepository.getTopRatedMovies(callback,currentPage)
    }

    fun showFavoritesSQLite(){
        val favoritesM = dbHelper.allFavorites
        setAdapter(MoviesAdapterFragment(favoritesM, view!!.context))
    }

    //CALLBACK TO BRING DATA FROM ADAPTER
    override fun getItemMovie(movie: Movie) {
        Log.d("Movie", movie.original_language)
        //call listenerAdapter activity
        if (listenerListFragment != null) {
            listenerListFragment!!.sendMovie(movie)
        }
    }

    private fun setListener(adapter: MoviesAdapterFragment) {
        adapter.listenerAdapter = this
    }

    fun setAdapter(adapter:MoviesAdapterFragment){
        movieList.adapter = adapter
        setListener(adapter)
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

    fun showFavorites(){
        val favoritesM = ArrayList<Movie>()
        val sharedPreferences = context!!.getSharedPreferences("movieapppreference", Context.MODE_PRIVATE)
        val allPreferences = sharedPreferences.all
        val item =  allPreferences.entries.iterator()

        while (item.hasNext()){
            val pair = item.next()
            //bring favorite as json through a key
            val sMov = sharedPreferences.getString(pair.key, "")
            //convert json to object using gson library
            val objMovie = Gson().fromJson(sMov, Movie::class.java)
            favoritesM.add(objMovie)
            Log.i("check movie", objMovie.title)
        }
        Log.i("favoritos", allPreferences.size.toString())
        setAdapter(MoviesAdapterFragment(favoritesM, view!!.context))
    }
}
