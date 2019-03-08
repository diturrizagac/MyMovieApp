package com.belatrixsf.mymovieapp.model.entity
import java.io.Serializable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Movie(
    vote_count: Int,
    id: Int,
    vote_average: Float,
    title: String,
    popularity: Double,
    poster_path: String,
    original_language: String,
    original_title: String,
    genre_ids: List<Int>,
    backdrop_path: String,
    adult: Boolean,
    overview: String,
    release_date: String
): Serializable {
    @SerializedName("vote_count")
    @Expose
    var vote_count = vote_count

    @SerializedName("id")
    @Expose
    var id = id

    @SerializedName("vote_average")
    @Expose
    var vote_average = vote_average

    @SerializedName("title")
    @Expose
    var title = title

    @SerializedName("popularity")
    @Expose
    var popularity = popularity

    @SerializedName("poster_path")
    @Expose
    var poster_path = poster_path

    @SerializedName("original_language")
    @Expose
    var original_language= original_language

    @SerializedName("original_title")
    @Expose
    var original_title = original_title

    @SerializedName("genre_ids")
    @Expose
    var genre_ids = genre_ids

    @SerializedName("backdrop_path")
    @Expose
    var backdrop_path = backdrop_path

    @SerializedName("adult")
    @Expose
    var adult = adult

    @SerializedName("overview")
    @Expose
    var overview= overview

    @SerializedName("release_date")
    @Expose
    var release_date = release_date

}
