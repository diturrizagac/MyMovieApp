package com.belatrixsf.mymovieapp.data

import android.provider.BaseColumns

class FavoriteContract {
    object FavoriteEntry : BaseColumns {
        const val TABLE_NAME = "favorite"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "tittle"
        const val COLUMN_RELEASE = "release"
        const val COLUMN_RATING = "rating"
        const val COLUMN_POSTER_PATH = "poster"
        const val COLUMN_OVERVIEW = "overview"
    }
}