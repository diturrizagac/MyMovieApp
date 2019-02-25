package com.belatrixsf.mymovieapp.model.entity

class Movie {
    var titleMovie:String? = null
    var categoryMovie:String? = null
    var descriptionMovie:String? = null
    var releaseDate:String? = null
    var thumbnailMovie:Int? = 0
    constructor(){}
    constructor(
        titleMovie: String?,
        categoryMovie: String?,
        descriptionMovie: String?,
        releaseDate: String?,
        thumbnailMovie: Int?
    ) {
        this.titleMovie = titleMovie
        this.categoryMovie = categoryMovie
        this.descriptionMovie = descriptionMovie
        this.releaseDate = releaseDate
        this.thumbnailMovie = thumbnailMovie
    }

}