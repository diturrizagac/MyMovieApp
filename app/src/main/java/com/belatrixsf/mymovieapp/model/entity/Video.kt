package com.belatrixsf.mymovieapp.model.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Video(
    id: String,
    iso_639_1: String,
    iso_3166_1: String,
    key: String,
    name: String,
    site: String,
    size: Int,
    type: String
) {
    @SerializedName("id")
    @Expose
    val id = id

    @SerializedName("iso_639_1")
    @Expose
    val iso_639_1 = iso_639_1

    @SerializedName("iso_3166_1")
    @Expose
    val iso_3166_1 = iso_3166_1

    @SerializedName("key")
    @Expose
    val key = key

    @SerializedName("name")
    @Expose
    val name = name

    @SerializedName("site")
    @Expose
    val site = site

    @SerializedName("size")
    @Expose
    val size = size

    @SerializedName("type")
    @Expose
    val type = type

}