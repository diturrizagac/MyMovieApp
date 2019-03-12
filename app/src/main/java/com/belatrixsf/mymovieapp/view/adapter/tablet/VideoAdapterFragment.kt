package com.belatrixsf.mymovieapp.view.adapter.tablet

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.belatrixsf.mymovieapp.R
import com.belatrixsf.mymovieapp.api.Api
import com.belatrixsf.mymovieapp.model.entity.Video
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class VideoAdapterFragment(private val videos: List<Video>, private val mContext: Context) :
    RecyclerView.Adapter<VideoAdapterFragment.VideoViewHolder>(){

    var listenerAdapterF: OnClickItemVideoAdapterFragListener? = null

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



    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var imageVideo: ImageView = itemView.findViewById(R.id.item_video_cover)

        val myView = itemView.setOnClickListener {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val videoSelected = videos[position]
                if(listenerAdapterF!=null)
                    listenerAdapterF!!.goToYoutubeIntent(videoSelected)
            }
        }

        fun bindVideo(video: Video) {
            Log.i("videooo","${Api.YOUTUBE_THUMBNAIL_URL}${video.key}/default.jpg")
            Glide.with(itemView)
                .load("${Api.YOUTUBE_THUMBNAIL_URL}${video.key}/default.jpg")
                .apply(RequestOptions.placeholderOf(R.color.colorPrimaryDark))
                .into(imageVideo)
        }
    }

    interface OnClickItemVideoAdapterFragListener{
        fun goToYoutubeIntent(video:Video)
    }
}