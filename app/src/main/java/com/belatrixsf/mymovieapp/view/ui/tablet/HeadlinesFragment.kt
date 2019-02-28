package com.belatrixsf.mymovieapp.view.ui.tablet

import android.app.Activity
import androidx.fragment.app.ListFragment
import com.belatrixsf.mymovieapp.view.ui.tablet.main.OnHeadlineSelectedListener

class HeadlinesFragment: ListFragment() {
    lateinit var callback: OnHeadlineSelectedListener

    fun setDetailsOnFragment(activity: Activity){
        callback = activity as OnHeadlineSelectedListener
    }
}