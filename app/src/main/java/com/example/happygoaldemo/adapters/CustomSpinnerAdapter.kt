package com.example.happygoaldemo.adapters

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatCheckedTextView
import com.example.happygoaldemo.data.model.Drink
import kotlin.collections.ArrayList

    class CustomSpinnerAdapter(context: Context, resource: Int, private val campo:String, private val objects: ArrayList<Any>) : ArrayAdapter<Any>(context, resource, objects) {

    override fun getCount(): Int {
        return super.getCount()
    }

    override fun getItem(position: Int): Any? {
        return super.getItem(position)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var label = super.getView(position, convertView, parent) as AppCompatCheckedTextView
        label.setTextColor(Color.BLACK)
        var campo = objects[position].javaClass.getDeclaredField(campo);
        campo.isAccessible = true
        label.text = campo.get(objects[position]).toString()
        return label
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var label = super.getView(position, convertView, parent) as AppCompatCheckedTextView
        label.setTextColor(Color.BLACK)
        var campo = objects[position].javaClass.getDeclaredField(campo);
        campo.isAccessible = true
        label.text = campo.get(objects[position]).toString()
        return label
    }
}