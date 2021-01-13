package com.example.happygoaldemo.tools

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.example.happygoaldemo.R
import com.google.android.material.appbar.AppBarLayout

class Tools {
    fun savePreferences(activity: FragmentActivity?, name: String, value: String, type: Int){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            when(type){
                1 -> putString(name, value)
                2 -> putBoolean(name, value.toBoolean())
                3 -> putLong(name, value.toLong())
            }
            commit()
        }
    }

    fun ocultaToolBar(activity: FragmentActivity?){
        val appBarLayout = activity?.findViewById<AppBarLayout>(R.id.app_bar_layout)
        val lp = appBarLayout?.layoutParams
        lp?.height = 0;
        appBarLayout?.layoutParams = lp
    }

    fun muestraToolBar(activity: FragmentActivity?){
        val appBarLayout = activity?.findViewById<AppBarLayout>(R.id.app_bar_layout)
        val lp = appBarLayout?.layoutParams
        val x: IntArray = intArrayOf(android.R.attr.actionBarSize)
        val attibutes = activity?.obtainStyledAttributes(x)
        lp?.height = attibutes?.getDimension(0, 1F)?.toInt()
        //.resources?.getInteger()?.toInt();
        appBarLayout?.layoutParams = lp
    }
}