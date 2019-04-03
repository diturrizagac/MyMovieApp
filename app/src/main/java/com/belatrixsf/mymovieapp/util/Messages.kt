package com.belatrixsf.mymovieapp.util

import android.content.Context
import android.widget.Toast

class Messages {

    fun showErrorMessage(context: Context){
        Toast.makeText(context, "Please check your internet connection.", Toast.LENGTH_SHORT)
            .show()
    }

    fun showErrorMessage(context: Context, message: String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT)
            .show()
    }
}