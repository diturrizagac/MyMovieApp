package com.belatrixsf.mymovieapp.view.ui.mobile.detail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.belatrixsf.mymovieapp.OnGetVideoCallback
import com.belatrixsf.mymovieapp.R
import com.belatrixsf.mymovieapp.api.Api.IMAGE_BASE_URL
import com.belatrixsf.mymovieapp.api.Api.YOUTUBE_VIDEO_URL
import com.belatrixsf.mymovieapp.model.entity.Movie
import com.belatrixsf.mymovieapp.model.entity.Video
import com.belatrixsf.mymovieapp.repository.VideosRepository
import com.belatrixsf.mymovieapp.view.adapter.VideoAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout

class MovieDetailActivity : AppCompatActivity(), VideoAdapter.OnClickItemVideoAdapterListener {
    private var titleTv: TextView? = null
    private var imageIv: ImageView? = null
    private var releaseDateTv: TextView? = null
    private var overviewTv: TextView? = null
    private var voteAverageTv: RatingBar? = null
    lateinit var id : String
    lateinit var videoList: RecyclerView
    private var videoAdapter : VideoAdapter? = null
    private var videosRepository = VideosRepository.getInstance()
    lateinit var movie : Movie
    lateinit var toolbar:Toolbar
    lateinit var collapsingToolbar : CollapsingToolbarLayout
    lateinit var appBar : AppBarLayout
    lateinit var layoutManagerR: LinearLayoutManager
    lateinit var nestedScrollView: NestedScrollView

    @SuppressWarnings("ConstantConditions")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        initializeUI()
        showVideos()
    }

    private fun initializeUI(){
        titleTv = findViewById(R.id.detail_header_title)
        overviewTv = findViewById(R.id.detail_body_overview)
        releaseDateTv = findViewById(R.id.detail_header_release)
        voteAverageTv = findViewById(R.id.detail_header_star)
        imageIv = findViewById(R.id.movie_detail_poster)
        videoList = findViewById(R.id.detail_body_recyclerView_trailers)
        toolbar = findViewById(R.id.movie_detail_toolbar)
        collapsingToolbar = findViewById(R.id.detail_collapse_toolbar)
        appBar = findViewById(R.id.details_appBar)
        nestedScrollView = findViewById(R.id.details_nested_scrollView)
        layoutManagerR = LinearLayoutManager(this)

        //arction bar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //collapsing

        //setting Videos
        videoList.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        // Recieve data
        val movie = intent.extras!!.getSerializable("movie") as Movie // casteo // oncreatemenu
        id = movie.id.toString()
        getMovie(movie)

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
                    setListener()
                    videoList.adapter = videoAdapter
                }
                override fun onError() {
                    Toast.makeText(this@MovieDetailActivity, "VIDEO Please check your internet connectionVIDEOS.", Toast.LENGTH_SHORT)
                        .show()
                }

            }, movie.id
        )
    }

    private fun getMovie(movie:Movie){
        this.movie = movie
    }

    private fun setListener() {
        videoAdapter!!.listenerAdapter = this

    }

    override fun goToYoutubeIntent(video:Video){
        Log.i("goYoutube","$YOUTUBE_VIDEO_URL${video.key}")

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$YOUTUBE_VIDEO_URL${video.key}"))
        startActivity(intent)
    }

}
