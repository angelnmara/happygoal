package com.example.happygoaldemo.tools

import android.content.Context
import android.preference.PreferenceManager
import androidx.fragment.app.FragmentActivity
import com.example.happygoaldemo.R
import com.google.android.material.appbar.AppBarLayout
import java.util.*


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

    fun getDayOfWeekEspanniol(date: Date?, context: Context?):String?{
        //val calendar: Calendar = Calendar.getInstance()
        val c = Calendar.getInstance()
        c.time = date
        val dayOfWeek = c.get(Calendar.DAY_OF_WEEK)
        return when(dayOfWeek){
            Calendar.SUNDAY -> context?.getString(R.string.domingo)
            Calendar.MONDAY -> context?.getString(R.string.lunes)
            Calendar.TUESDAY -> context?.getString(R.string.martes)
            Calendar.WEDNESDAY -> context?.getString(R.string.miercoles)
            Calendar.THURSDAY -> context?.getString(R.string.jueves)
            Calendar.FRIDAY -> context?.getString(R.string.viernes)
            Calendar.SATURDAY -> context?.getString(R.string.sabado)
            else -> ""
        }
    }

    fun getDay(date: Date?):String{
        //val calendar: Calendar = Calendar.getInstance()
        val c = Calendar.getInstance()
        c.time = date
        return c.get(Calendar.DAY_OF_MONTH).toString()
    }

    fun getHora(date: Date?, context: Context?):String{
        //val calendar: Calendar = Calendar.getInstance()
        val c = Calendar.getInstance()
        c.time = date
        return c.get(Calendar.HOUR).toString().padStart(2, '0') + ":" + c.get(Calendar.MINUTE).toString().padStart(2, '0') + ":" + c.get(Calendar.SECOND).toString().padStart(2, '0') + " " + getAMPM(c.get(Calendar.AM_PM), context)
     }

    fun getAMPM(id:Int, context: Context?):String{
        return when(id){
            Calendar.AM->context?.getString(R.string.am).toString()
            Calendar.PM->context?.getString(R.string.pm).toString()
            else -> context?.getString(R.string.am).toString()
        }
    }

    fun getFaceResource(id:Int?):Int{
        return when(id){
            1->R.drawable.ic_feliz
            2->R.drawable.ic_motivado
            3->R.drawable.ic_tranquilo
            4->R.drawable.ic_estresado
            5->R.drawable.ic_enojado
            else -> R.drawable.ic_feliz
        }
    }

}