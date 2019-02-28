package com.belatrixsf.mymovieapp.view.ui.mobile.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.belatrixsf.mymovieapp.OnGetVideoCallback
import com.belatrixsf.mymovieapp.R
import com.belatrixsf.mymovieapp.model.entity.Movie
import com.belatrixsf.mymovieapp.model.entity.Video
import com.belatrixsf.mymovieapp.repository.VideosRepository
import com.belatrixsf.mymovieapp.view.adapter.MoviesAdapter.Companion.IMAGE_BASE_URL
import com.belatrixsf.mymovieapp.view.adapter.VideoAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MovieDetailActivity : AppCompatActivity() {
    private var titleTv: TextView? = null
    private var imageIv: ImageView? = null
    private var releaseDateTv: TextView? = null
    private var overviewTv: TextView? = null
    private var voteAverageTv: RatingBar? = null
    lateinit var id : String
    private var videoList: RecyclerView? = null
    private var videoAdapter : VideoAdapter? = null
    private var videosRepository = VideosRepository.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //val fragmentMovieDetailFragment = supportFragmentManager.findFragmentById(R.id.fragment_details_tablet) as MovieDetailFragment?
        initializeUI()
        //showVideos(id)

    }

    fun initializeUI(){
        titleTv = findViewById(R.id.detail_header_title)
        overviewTv = findViewById(R.id.detail_body_overview)
        releaseDateTv = findViewById(R.id.detail_header_release)
        voteAverageTv = findViewById(R.id.detail_header_star)
        imageIv = findViewById(R.id.movie_detail_poster)

        // Recieve data
        val movie = intent.extras!!.getSerializable("movie") as Movie // casteo // oncreatemenu
        id = movie.id.toString()

        // Setting values
        titleTv!!.text = movie.title
        overviewTv!!.text = movie.overview
        releaseDateTv!!.text = "Release Date : ${movie.release_date}"
        voteAverageTv!!.rating = movie.vote_average /2
        Glide.with(this)
            .load("$IMAGE_BASE_URL${movie.backdrop_path}")
            .apply(RequestOptions.placeholderOf(R.color.colorPrimaryDark))
            .into(imageIv!!)

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    fun showVideos(id:String){
        videosRepository.getVideos(
            id,
            object: OnGetVideoCallback {
                override fun onSuccess(videos: List<Video>) {
                    videoAdapter = VideoAdapter(videos)
                    videoList!!.adapter = videoAdapter
                }
                override fun onError() {
                    Toast.makeText(this@MovieDetailActivity, "Please check your internet connection.", Toast.LENGTH_SHORT)
                        .show()
                }

            }
        )
    }
}
