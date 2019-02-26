package com.belatrixsf.mymovieapp.model.entity
import java.io.Serializable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class MovieEntity(
    vote_count: Int,
    id: Int,
    vote_average: Double,
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
    val vote_count = vote_count

    @SerializedName("id")
    @Expose
    val id = id

    @SerializedName("vote_average")
    @Expose
    val vote_average = vote_average

    @SerializedName("title")
    @Expose
    val title = title

    @SerializedName("popularity")
    @Expose
    val popularity = popularity

    @SerializedName("poster_path")
    @Expose
    val poster_path = poster_path

    @SerializedName("original_language")
    @Expose
    val original_language= original_language

    @SerializedName("original_title")
    @Expose
    val original_title = original_title

    @SerializedName("genre_ids")
    @Expose
    val genre_ids = genre_ids

    @SerializedName("backdrop_path")
    @Expose
    val backdrop_path = backdrop_path

    @SerializedName("adult")
    @Expose
    val adult = adult

    @SerializedName("overview")
    @Expose
    val overview= overview

    @SerializedName("release_date")
    @Expose
    val release_date = release_date

}
