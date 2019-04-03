package com.belatrixsf.mymovieapp.view.adapter.mobile

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.belatrixsf.mymovieapp.R
import com.belatrixsf.mymovieapp.api.Api.YOUTUBE_THUMBNAIL_URL
import com.belatrixsf.mymovieapp.model.entity.Video
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class VideoAdapter(private val videos: List<Video>, private val mContext: Context) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    var listenerAdapter: OnClickItemVideoAdapterListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = videos[position]
        holder.bindVideo(video)
    }

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageVideo: ImageView = itemView.findViewById(R.id.item_video_cover)

        val myView = itemView.setOnClickListener {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val videoSelected = videos[position]
                if(listenerAdapter!=null)
                    listenerAdapter!!.goToYoutubeIntent(videoSelected)
            }
        }

        fun bindVideo(video: Video) {
            Log.i("videooo","$YOUTUBE_THUMBNAIL_URL${video.key}/default.jpg")
            Glide.with(itemView)
                .load("$YOUTUBE_THUMBNAIL_URL${video.key}/default.jpg")
                .apply(RequestOptions.placeholderOf(R.drawable.ic_launcher_foreground))
                .into(imageVideo)
        }
    }

    interface OnClickItemVideoAdapterListener{
        fun goToYoutubeIntent(video:Video)
    }

}