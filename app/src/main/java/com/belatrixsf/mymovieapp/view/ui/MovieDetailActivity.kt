package com.belatrixsf.mymovieapp.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.belatrixsf.mymovieapp.R

class MovieDetailActivity : AppCompatActivity() {
    private var tvtitle: TextView? = null
    private var tvdescription: TextView? = null
    private var tvcategory: TextView? = null
    private var img: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        tvtitle = findViewById(R.id.txttitle)
        tvdescription = findViewById(R.id.txtDesc)
        img = findViewById(R.id.ivthumbnail)

        // Recieve data
        val intent = intent
        val Title = intent.extras!!.getString("Title")
        val Description = intent.extras!!.getString("Description")
        val image = intent.extras!!.getInt("Thumbnail")

        // Setting values

        tvtitle!!.text = Title
        tvdescription!!.text = Description
        img!!.setImageResource(image)




    }
}
