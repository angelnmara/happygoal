package com.example.happygoaldemo

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.happygoaldemo.data.model.Calificacion
import com.example.happygoaldemo.databinding.FragmentEstadisticaPersonalListBinding

/**
 * [RecyclerView.Adapter] that can display a [Calificacion].
 * TODO: Replace the implementation with code for your data type.
 */
class MyItemRecyclerViewAdapter(
    private val values: List<Calificacion>
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentEstadisticaPersonalListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.idCalificacion.toString()
        holder.contentView.text = item.idUsuario
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentEstadisticaPersonalListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}