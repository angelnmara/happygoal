package com.example.happygoaldemo.tools

import android.content.Context
import android.preference.PreferenceManager
import androidx.fragment.app.FragmentActivity
import com.example.happygoaldemo.R
import com.google.android.material.appbar.AppBarLayout


class Tools {
    fun savePreferences(context: Context?, name: String, value: String, type: Int){
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)?: return
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

    fun setDefaults(key: String?, value: String?, context: Context?) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()
        editor.putString(key, value)
        editor.commit()
    }

    fun getDefaultsString(key: String?, context: Context?): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getString(key, "")
    }

    fun getDefaultsBolean(key: String?, context: Context?): Boolean {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getBoolean(key, false)
    }

    fun getDefaultsLong(key: String?, context: Context?): Long {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getLong(key, 0L)
    }

}