package com.belatrixsf.mymovieapp.model.entity
import java.io.Serializable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName




class MovieEntity(
    id: Int,
    voteCount: Int,
    voteAverage: Int,
    title: String,
    rating: Double,
    trailer: String,
    overview: String,
    release: String,
    poster: String,
    reviewUsers: String,
    genreIds: List<Int>
) {

    @SerializedName("id")
    @Expose
    private val id: Int = id

    @SerializedName("title")
    @Expose
    private val title: String? = title

    @SerializedName("poster_path")
    @Expose
    private val posterPath: String? = poster

    @SerializedName("release_date")
    @Expose
    private val releaseDate: String? = release

    @SerializedName("vote_average")
    @Expose
    private val rating: Double = rating

    @SerializedName("genre_ids")
    @Expose
    private val genreIds: List<Int> = genreIds



//    val idMovie = id
//    val voteCountMovie = voteCount
//    val voteAverageMovie = voteAverage
//    val titleMovie = title
//    val popularityMovie = popularity
//    val trailerMovie = trailer
//    val overviewMovie = overview
//    val releaseDateMovie = release
//    val posterMovie = poster
//    val reviewUsersMovie = reviewUsers
}
