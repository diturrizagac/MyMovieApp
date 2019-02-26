package com.belatrixsf.mymovieapp.view.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.belatrixsf.mymovieapp.R

class MovieDetailActivity : AppCompatActivity() {
    private var tvtitle: TextView? = null
    private var tvdescription: TextView? = null
    private var img: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        tvtitle = findViewById(R.id.txttitle)
        tvdescription = findViewById(R.id.txtDesc)
        img = findViewById(R.id.ivthumbnail)

        // Recieve data
        val intent = intent
        val title = intent.extras!!.getString("Title")
        val description = intent.extras!!.getString("Description")
        val image = intent.extras!!.getInt("Thumbnail")

        // Setting values
        tvtitle!!.text = title
        tvdescription!!.text = description
        img!!.setImageResource(image)




    }
}
