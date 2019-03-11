package com.belatrixsf.mymovieapp.data
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.provider.BaseColumns._ID
import android.util.Log
import com.belatrixsf.mymovieapp.data.FavoriteContract.*
import com.belatrixsf.mymovieapp.data.FavoriteContract.FavoriteEntry.COLUMN_ID
import com.belatrixsf.mymovieapp.data.FavoriteContract.FavoriteEntry.COLUMN_OVERVIEW
import com.belatrixsf.mymovieapp.data.FavoriteContract.FavoriteEntry.COLUMN_POSTER_PATH
import com.belatrixsf.mymovieapp.data.FavoriteContract.FavoriteEntry.COLUMN_RATING
import com.belatrixsf.mymovieapp.data.FavoriteContract.FavoriteEntry.COLUMN_RELEASE
import com.belatrixsf.mymovieapp.data.FavoriteContract.FavoriteEntry.COLUMN_TITLE
import com.belatrixsf.mymovieapp.data.FavoriteContract.FavoriteEntry.TABLE_NAME
import com.belatrixsf.mymovieapp.model.entity.Movie

class FavoriteDbHelper(context:Context): SQLiteOpenHelper(context,DATABASE_NAME,null, DATABASE_VERSION) {
    lateinit var dbHandler : SQLiteOpenHelper
    lateinit var database: SQLiteDatabase
    companion object {
        const val DATABASE_NAME = "favoriteDb.db"
        const val DATABASE_VERSION = 1
        const val LOGTAG = "FAVORITE"
        const val SQL_CREATE_FAVORITE_TABLE =
            "CREATE TABLE $TABLE_NAME (" +
                    "$_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_ID INTEGER, " +
                    "$COLUMN_TITLE TEXT, " +
                    "$COLUMN_RELEASE TEXT, " +
                    "$COLUMN_RATING REAL, " +
                    "$COLUMN_POSTER_PATH TEXT, " +
                    "$COLUMN_OVERVIEW TEXT" +
                    ");"

        const val SQL_DELETE_FAVORITE_TABLE = "DROP TABLE IF EXISTS ${FavoriteEntry.TABLE_NAME}"

    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_FAVORITE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_FAVORITE_TABLE)
        onCreate(db)
    }

    fun open(){
        Log.i(LOGTAG,"db opened")
        database = dbHandler.writableDatabase
    }

    fun closed(){
        Log.i(LOGTAG,"db closed")
        dbHandler.close()
    }

    fun addFavorite(movie: Movie){
        Log.i("dbbbbb", "before write DB")
        val db = writableDatabase
        Log.i("dbbbbb", "after write DB")
        val values = ContentValues()
        values.put(COLUMN_ID,movie.id)
        values.put(COLUMN_TITLE,movie.title)
        values.put(COLUMN_RELEASE,movie.release_date)
        values.put(COLUMN_RATING,movie.vote_average)
        values.put(COLUMN_POSTER_PATH,movie.poster_path)
        values.put(COLUMN_OVERVIEW,movie.overview)

        db.insert(TABLE_NAME,null,values)
        db.close()
    }

    fun deleteFavorites(id: Int){
        val db = writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID = $id", null)
        db.close()
    }

    fun isExistFavorite(id:Int):Boolean{
        val db = readableDatabase

        val selection = "$COLUMN_ID = ? "
        val selectionArgument = arrayOf(id.toString())
        val cursor = db.query(
            TABLE_NAME,
            null, // es un array
            selection,
            selectionArgument,
            null,
            null,
            null
        )
        if(cursor.moveToFirst()){
            Log.v("entro?"," SI ENTRO")
            return true
        }
        cursor.close()
        db.close()
        return false
    }

    val allFavorites: List<Movie>
        get() {
            val columns = arrayOf(
                _ID,
                COLUMN_ID,
                COLUMN_TITLE,
                COLUMN_RELEASE,
                COLUMN_RATING,
                COLUMN_POSTER_PATH,
                COLUMN_OVERVIEW
            )
            val sortOrder = "$_ID ASC"
            val favoriteList = ArrayList<Movie>()
            val db = readableDatabase
            val cursor = db.query(
                TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder
            )
            if(cursor.moveToFirst()){
                do{
                    val id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID)))
                    val title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
                    val release = cursor.getString(cursor.getColumnIndex(COLUMN_RELEASE))
                    val voteAverage = java.lang.Float.parseFloat(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_RATING)))
                    val poster = cursor.getString(cursor.getColumnIndex(COLUMN_POSTER_PATH))
                    val overview = cursor.getString(cursor.getColumnIndex(COLUMN_OVERVIEW))
                    val listAux = ArrayList<Int>() as List<Int>

                    val movie = Movie(0,0, 0F,"",
                        0.0,"","","",
                        listAux,"",false,"","")
                    movie.id = id
                    movie.title = title
                    movie.release_date = release
                    movie.vote_average = voteAverage
                    movie.poster_path = poster
                    movie.overview = overview
                    favoriteList.add(movie)
                }while (cursor.moveToNext())
            }
            cursor.close()
            db.close()
            return favoriteList
        }
}