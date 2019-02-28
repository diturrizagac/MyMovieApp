package com.belatrixsf.mymovieapp.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.belatrixsf.mymovieapp.R
import com.belatrixsf.mymovieapp.model.entity.Movie
import com.belatrixsf.mymovieapp.model.entity.Video
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class VideoAdapter(private val videos: List<Video>) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {
    companion object {
        val YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/"
        val YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v="
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoAdapter.VideoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    override fun onBindViewHolder(holder: VideoAdapter.VideoViewHolder, position: Int) {
        val video = videos[position]
    }

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageVideo: ImageView = itemView.findViewById(R.id.item_video_cover)

        val myView = itemView.setOnClickListener {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val videoSelected = videos[position]

            }
        }

        fun bindVideo(video: Video) {
            //itemView.setOnClickListener(onClickListener)
            //title.text = movie.title
            Glide.with(itemView)
                .load("$YOUTUBE_THUMBNAIL_URL${video.key}/default.jpg")
                .apply(RequestOptions.placeholderOf(R.color.colorPrimaryDark))
                .into(imageVideo)
        }

    }
}