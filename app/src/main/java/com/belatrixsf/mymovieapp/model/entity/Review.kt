package com.belatrixsf.mymovieapp.model.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Review (
    author: String,
    content: String,
    id: String,
    url: String
): Serializable{

    @SerializedName("author")
    @Expose
    val author = author

    @SerializedName("content")
    @Expose
    val content = content

    @SerializedName("id")
    @Expose
    val id = id

    @SerializedName("url")
    @Expose
    val url = url

}