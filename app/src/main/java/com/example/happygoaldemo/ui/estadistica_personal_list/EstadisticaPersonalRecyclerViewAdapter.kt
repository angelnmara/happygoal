package com.example.happygoaldemo.ui.estadistica_personal_list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.happygoaldemo.R
import com.example.happygoaldemo.data.model.Calificacion
import com.example.happygoaldemo.databinding.FragmentEstadisticaPersonalListBinding
import com.example.happygoaldemo.tools.Tools
import java.util.*

/**
 * [RecyclerView.Adapter] that can display a [Calificacion].
 * TODO: Replace the implementation with code for your data type.
 */
class EstadisticaPersonalRecyclerViewAdapter(
        private val values: List<Calificacion>,
        private val context: Context
) : RecyclerView.Adapter<EstadisticaPersonalRecyclerViewAdapter.ViewHolder>() {

    var tools = Tools()

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
        if(position % 2 == 0)
        {
            holder.rootView.setBackgroundResource(R.color.white);
        }
        else
        {
            holder.rootView.setBackgroundResource(R.color.zebraClaroReciclerView);
        }
        val item = values[position]
        holder.idView.text = tools.getHora(item.fechaCreacion, context)
        holder.contentView.text = item.emocion
        holder.txtDia.text = tools.getDayOfWeekEspanniol(item.fechaCreacion, context)
        holder.txtDiaNumero.text = tools.getDay(item.fechaCreacion)
        holder.imgSentimiento.setImageResource(tools.getFaceResource(item.calificacion))
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentEstadisticaPersonalListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content
        val txtDiaNumero: TextView = binding.txtDiaNumero
        val txtDia: TextView = binding.txtDia
        val imgSentimiento: ImageView = binding.imgSentimiento
        val rootView:LinearLayout = binding.rootView

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}