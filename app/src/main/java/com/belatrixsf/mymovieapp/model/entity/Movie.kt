package com.belatrixsf.mymovieapp.model.entity

import java.io.Serializable

class Movie : Serializable {
    var vote_count: Int? = null
    var id: Int? = null
    var vote_average: Double? = null
    var title: String? = null
    var popularity: Double? = null
    var poster_path: String? = null
    var original_language: String? = null
    var original_title: String? = null
    var genre_ids: List<Int>? = null
    var backdrop_path: String? = null
    var adult: Boolean? = null
    var overview: String? = null
    var release_date: String? = null

    constructor(){}
    constructor(
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
    ) {
        this.vote_count = vote_count
        this.id = id
        this.vote_average = vote_average
        this.title = title
        this.popularity = popularity
        this.poster_path = poster_path
        this.original_language = original_language
        this.original_title = original_title
        this.genre_ids = genre_ids
        this.backdrop_path = backdrop_path
        this.adult = adult
        this.overview = overview
        this.release_date = release_date
    }
}