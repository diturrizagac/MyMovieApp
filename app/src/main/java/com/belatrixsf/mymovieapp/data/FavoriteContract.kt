package com.belatrixsf.mymovieapp.data

import android.provider.BaseColumns

class FavoriteContract {
    class FavoriteEntry : BaseColumns{
            val TABLE_NAME = "favorite"
            val COLUMN_MOVIEID = "movieid"
            val COLUMN_TITLE = "tittle"
            val COLUMN_RELEASEDATE = "release"
            val COLUMN_RATING = "rating"
            val COLUMN_POSTER_PATH = "posterpath"
            val COLUMN_OVERVIEW = "overview"

    }
}