package com.belatrixsf.mymovieapp.view.ui.mobile.detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.belatrixsf.mymovieapp.OnGetReviewCallback
import com.belatrixsf.mymovieapp.OnGetVideoCallback
import com.belatrixsf.mymovieapp.R
import com.belatrixsf.mymovieapp.api.Api.IMAGE_BASE_URL
import com.belatrixsf.mymovieapp.api.Api.YOUTUBE_VIDEO_URL
import com.belatrixsf.mymovieapp.data.FavoriteDbHelper
import com.belatrixsf.mymovieapp.model.entity.Movie
import com.belatrixsf.mymovieapp.model.entity.Review
import com.belatrixsf.mymovieapp.model.entity.Video
import com.belatrixsf.mymovieapp.repository.ReviewsRepository
import com.belatrixsf.mymovieapp.repository.VideosRepository
import com.belatrixsf.mymovieapp.view.adapter.ReviewAdapter
import com.belatrixsf.mymovieapp.view.adapter.VideoAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.gson.Gson

class MovieDetailActivity : AppCompatActivity(), VideoAdapter.OnClickItemVideoAdapterListener {
    private var titleTv: TextView? = null
    private var imageIv: ImageView? = null
    private var releaseDateTv: TextView? = null
    private var overviewTv: TextView? = null
    private var voteAverageTv: RatingBar? = null
    lateinit var videoList: RecyclerView
    lateinit var reviewList: RecyclerView
    private var videoAdapter : VideoAdapter? = null
    private var reviewAdapter : ReviewAdapter? = null
    private var videosRepository = VideosRepository.getInstance()
    private var reviewRepository = ReviewsRepository.getInstance()
    lateinit var movie : Movie
    lateinit var toolbar:Toolbar
    lateinit var collapsingToolbar : CollapsingToolbarLayout
    lateinit var appBar : AppBarLayout
    lateinit var favoriteBtn : Button
    private var favoriteDbHelper = FavoriteDbHelper(this)

    @SuppressWarnings("ConstantConditions")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        initializeUI()
        showVideos()
        showReviews()
    }


    @SuppressLint("WrongConstant")
    private fun initializeUI(){
        titleTv = findViewById(R.id.detail_header_title)
        overviewTv = findViewById(R.id.detail_body_overview)
        releaseDateTv = findViewById(R.id.detail_header_release)
        voteAverageTv = findViewById(R.id.detail_header_star)
        imageIv = findViewById(R.id.movie_detail_poster)

        toolbar = findViewById(R.id.movie_detail_toolbar)
        collapsingToolbar = findViewById(R.id.detail_collapse_toolbar)
        appBar = findViewById(R.id.details_appBar)
        favoriteBtn = findViewById(R.id.detail_favorite_button)

        videoList = findViewById(R.id.detail_body_recyclerView_trailers)
        reviewList = findViewById(R.id.detail_body_recyclerView_reviews)


        //arction bar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //setting Videos
        videoList.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        //setting Reviews
        reviewList.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        // Recieve data
        val movie = intent.extras!!.getSerializable("movie") as Movie // casteo // oncreatemenu
        //id = movie.id.toString()
        this.movie = movie

        // Setting values
        collapsingToolbar.title = movie.title
        titleTv!!.text = movie.title
        overviewTv!!.text = movie.overview
        releaseDateTv!!.text = "Release Date : ${movie.release_date}"
        voteAverageTv!!.rating = movie.vote_average /2
        Glide.with(this)
            .load("$IMAGE_BASE_URL${movie.backdrop_path}")
            .apply(RequestOptions.placeholderOf(R.color.background900))
            .into(imageIv!!)


        if(isFavoriteInSQLite()){
            favoriteBtn.text = "<3"
        } else {
            favoriteBtn.text = "Mark as Favorite"
        }

        favoriteBtn.setOnClickListener {
            //save as favorite using SQL
            saveMovieAsFavoriteSQLite(movie)

            //method to save as favorite using SharedPreference
            //saveMovieAsFavorite(movie)
        }
    }

    private fun isFavoriteInSQLite():Boolean{
        val favoritesDb = favoriteDbHelper.allFavorites
        if(favoritesDb.contains(movie)){
            Log.i("movie", "IsFavorite")
            return true
        }
        return false
    }


    private fun isFavorite():Boolean{
        val sharedPreferences = getSharedPreferences("movieapppreference", Context.MODE_PRIVATE)
        val allPreferences = sharedPreferences.all
        val item =  allPreferences.entries.iterator()

        while (item.hasNext()){
            val pair = item.next()
            //bring favorite as json through key
            val sMov = sharedPreferences.getString(pair.key, "")
            //convert json to object
            val objMovie = Gson().fromJson(sMov, Movie::class.java)
            //favoritesM.add(objMovie)
            //Log.i("check movie", "${objMovie.title}")
            if (objMovie.id== this.movie.id)
                return true
        }
        return false
        //Log.i("favoritos", allPreferences.size.toString())
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun showVideos(){
        videosRepository.getVideos(
            object: OnGetVideoCallback {
                override fun onSuccess(videos: List<Video>) {
                    videoAdapter = VideoAdapter(videos,this@MovieDetailActivity)
                    setListenerVideo()
                    videoList.adapter = videoAdapter
                }
                override fun onError() {
                    Toast.makeText(this@MovieDetailActivity, "VIDEO Please check your internet connectionVIDEOS.", Toast.LENGTH_SHORT)
                        .show()
                }

            }, movie.id
        )
    }
    private fun showReviews(){
        reviewRepository.getReviews(
            object: OnGetReviewCallback{
                override fun onSuccess(reviews: List<Review>) {
                    reviewAdapter = ReviewAdapter(reviews,this@MovieDetailActivity)
                    //listener
                    reviewList.adapter = reviewAdapter
                }

                override fun onError() {
                    Toast.makeText(this@MovieDetailActivity, "REVIEW Please check your internet connection.", Toast.LENGTH_SHORT)
                        .show()
                }

            },movie.id
        )
    }

    private fun setListenerVideo() {
        videoAdapter!!.listenerAdapter = this

    }

    override fun goToYoutubeIntent(video:Video){
        Log.i("goYoutube","$YOUTUBE_VIDEO_URL${video.key}")

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$YOUTUBE_VIDEO_URL${video.key}"))
        startActivity(intent)
    }


    private fun saveMovieAsFavorite(movie:Movie){
        val sharedPreferences = getSharedPreferences("movieapppreference", Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        val sMovie = Gson().toJson(movie)

        if(favoriteBtn.text.equals("Mark as Favorite")){
            //saving data on Shared Preferences
            Log.i("check movieAux", sMovie)
            editor.putString(movie.id.toString(), sMovie)
            editor.apply()
            favoriteBtn.text = "<3"
        } else {
            editor.remove(movie.id.toString())
            editor.commit()
            favoriteBtn.text = "Mark as Favorite"
            Log.i("removing movieAux",sMovie)
        }
    }

    private fun saveMovieAsFavoriteSQLite(movie: Movie){
        if(favoriteBtn.text == "Mark as Favorite"){
            favoriteDbHelper.addFavorite(movie)
            Log.i("pasoAddFavorite","paso!")
            favoriteBtn.text = "<3"
        } else {
            favoriteDbHelper.deleteFavorites(movie.id)
            favoriteBtn.text = "Mark as Favorite"

        }
    }
}
