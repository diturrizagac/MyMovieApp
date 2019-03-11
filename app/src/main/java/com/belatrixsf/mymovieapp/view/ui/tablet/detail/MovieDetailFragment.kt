package com.belatrixsf.mymovieapp.view.ui.tablet.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.belatrixsf.mymovieapp.OnGetReviewCallback
import com.belatrixsf.mymovieapp.OnGetVideoCallback
import com.belatrixsf.mymovieapp.R
import com.belatrixsf.mymovieapp.api.Api
import com.belatrixsf.mymovieapp.api.Api.IMAGE_BASE_URL
import com.belatrixsf.mymovieapp.data.FavoriteDbHelper
import com.belatrixsf.mymovieapp.model.entity.Movie
import com.belatrixsf.mymovieapp.model.entity.Review
import com.belatrixsf.mymovieapp.model.entity.Video
import com.belatrixsf.mymovieapp.repository.ReviewsRepository
import com.belatrixsf.mymovieapp.repository.VideosRepository
import com.belatrixsf.mymovieapp.view.adapter.ReviewAdapterFragment
import com.belatrixsf.mymovieapp.view.adapter.VideoAdapterFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import kotlinx.android.synthetic.main.layout_detail_body.*
import kotlinx.android.synthetic.main.layout_detail_header.*

class MovieDetailFragment : Fragment(), VideoAdapterFragment.OnClickItemVideoAdapterFragListener {
    private var titleTv: TextView? = null
    private lateinit var collapsingToolbar : CollapsingToolbarLayout
    private var imageIv: ImageView? = null
    private var releaseDateTv: TextView? = null
    private var overviewTv: TextView? = null
    private var voteAverageTv: RatingBar? = null
    lateinit var videoList: RecyclerView
    lateinit var reviewList: RecyclerView
    private var videoAdapter : VideoAdapterFragment? = null
    private var reviewAdapter : ReviewAdapterFragment? = null
    private var videosRepository = VideosRepository.getInstance()
    private var reviewRepository = ReviewsRepository.getInstance()
    lateinit var movie : Movie
    lateinit var favoriteBtn : Button
    private var favoriteDbHelper = FavoriteDbHelper(this.context!!)

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    @SuppressLint("WrongConstant")
    fun displayDetail(movie: Movie){
        titleTv = view!!.findViewById(R.id.detail_header_title)
        overviewTv = view!!.findViewById(R.id.detail_body_overview)
        releaseDateTv = view!!.findViewById(R.id.detail_header_release)
        voteAverageTv = view!!.findViewById(R.id.detail_header_star)
        imageIv = view!!.findViewById(R.id.movie_detail_poster)
        collapsingToolbar = view!!.findViewById(R.id.detail_collapse_toolbar)
        favoriteBtn = view!!.findViewById(R.id.detail_favorite_button)

        videoList = view!!.findViewById(R.id.detail_body_recyclerView_trailers)
        reviewList = view!!.findViewById(R.id.detail_body_recyclerView_reviews)

        videoList.layoutManager = LinearLayoutManager(view!!.context,LinearLayoutManager.HORIZONTAL,false)
        reviewList.layoutManager = LinearLayoutManager(view!!.context,LinearLayoutManager.VERTICAL,false)

        getMovie(movie)
        collapsingToolbar.title = movie.title
        titleTv!!.text = movie.title
        overviewTv!!.text = movie.overview
        releaseDateTv!!.text = "Release Date : ${movie.release_date}"
        voteAverageTv!!.rating = movie.vote_average /2
        Glide.with(this)
            .load("$IMAGE_BASE_URL${movie.backdrop_path}")
            .apply(RequestOptions.placeholderOf(R.color.background900))
            .into(imageIv!!)
        showVideos()
        showReviews()

        if(isFavoriteInSQLite()){
            favoriteBtn.text = "<3"
        } else {
            favoriteBtn.text = "Mark as Favorite"
        }

        favoriteBtn.setOnClickListener {
            //save as favorite using SQL
            saveMovieAsFavoriteSQLite(movie)
        }
    }

    private fun isFavoriteInSQLite():Boolean{
        return favoriteDbHelper.isExistFavorite(movie.id)
    }

    private fun showVideos(){
        videosRepository.getVideos(
            object: OnGetVideoCallback {
                override fun onSuccess(videos: List<Video>) {
                    videoAdapter = VideoAdapterFragment(videos,view!!.context)
                    setListenerVideo()
                    videoList.adapter = videoAdapter
                }
                override fun onError() {
                    Toast.makeText(view!!.context, "VIDEO Please check your internet connectionVIDEOS.", Toast.LENGTH_SHORT)
                        .show()
                }

            }, movie.id
        )
    }
    private fun showReviews(){
        reviewRepository.getReviews(
            object: OnGetReviewCallback {
                override fun onSuccess(reviews: List<Review>) {
                    reviewAdapter = ReviewAdapterFragment(reviews,view!!.context)
                    //listener
                    reviewList.adapter = reviewAdapter
                }

                override fun onError() {
                    Toast.makeText(view!!.context, "REVIEW Please check your internet connection.", Toast.LENGTH_SHORT)
                        .show()
                }

            },movie.id
        )
    }

    private fun getMovie(movie:Movie){
        this.movie = movie
    }

    private fun setListenerVideo() {
        videoAdapter!!.listenerAdapterF = this

    }

    override fun goToYoutubeIntent(video: Video) {
        Log.i("goYoutube","${Api.YOUTUBE_VIDEO_URL}${video.key}")

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("${Api.YOUTUBE_VIDEO_URL}${video.key}"))
        startActivity(intent)
    }

    private fun saveMovieAsFavoriteSQLite(movie: Movie){
        if(favoriteBtn.text == "Mark as Favorite"){
            favoriteDbHelper.addFavorite(movie)
            favoriteBtn.text = "<3"
            Log.i("addFavorite","movie saved SQLite")
        } else {
            favoriteDbHelper.deleteFavorites(movie.id)
            favoriteBtn.text = "Mark as Favorite"
            Log.i("deleteFavorite","movie removed SQLite")
        }
    }
}
