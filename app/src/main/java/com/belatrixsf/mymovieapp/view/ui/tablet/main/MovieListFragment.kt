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
import com.belatrixsf.mymovieapp.data.FavoriteDbHelper
import com.belatrixsf.mymovieapp.model.entity.Movie
import com.belatrixsf.mymovieapp.repository.MoviesRepository
import com.belatrixsf.mymovieapp.view.adapter.mobile.MoviesAdapter
import com.belatrixsf.mymovieapp.view.adapter.tablet.MoviesAdapterFragment
import com.belatrixsf.mymovieapp.view.ui.tablet.detail.MovieDetailFragment
import com.google.gson.Gson


class MovieListFragment : Fragment(), MoviesAdapterFragment.OnItemClickListener {
    lateinit var movieList: RecyclerView
    lateinit var movieAdapterF: MoviesAdapterFragment
    private var moviesRepository = MoviesRepository.getInstance()
    private lateinit var dbHelper :FavoriteDbHelper
    private var listenerListFragment: OnMovieListListener? = null


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
        moviesRepository.getMovies(object : OnGetMoviesCallback {
            override fun onSuccess(movies: List<Movie>) {
                setAdapter(MoviesAdapterFragment(movies, view!!.context))
                //movieAdapterF = MoviesAdapterFragment(movies, view!!.context)
                //connect Fragment - Adapter
                //movieList.adapter = movieAdapterF
                listenerListFragment!!.sendMovie(movies[0])
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
                setAdapter(MoviesAdapterFragment(movies, view!!.context))
                //movieAdapterF = MoviesAdapterFragment(movies, view!!.context)
                //movieList.adapter = movieAdapterF
                listenerListFragment!!.sendMovie(movies[0])
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
                setAdapter(MoviesAdapterFragment(movies, view!!.context))
                //movieAdapterF = MoviesAdapterFragment(movies, view!!.context)
                //movieList.adapter = movieAdapterF
                listenerListFragment!!.sendMovie(movies[0])
            }

            override fun onError() {
                Toast.makeText(view!!.context, "Please check your internet connection.", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun showFavoritesSQLite(){
        val favoritesM = dbHelper.allFavorites
        setAdapter(MoviesAdapterFragment(favoritesM, view!!.context))
        //movieAdapter = MoviesAdapter(favoritesM,this)
        //setAdapter(movieAdapter)
        //listenerListFragment!!.sendMovie(favoritesM[0])
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
        //movieAdapterF = MoviesAdapterFragment(favoritesM, view!!.context)
        //movieList.adapter = movieAdapterF
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
}
