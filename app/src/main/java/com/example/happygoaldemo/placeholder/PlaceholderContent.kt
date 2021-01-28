package com.example.happygoaldemo.placeholder

import com.example.happygoaldemo.api.RestApiService
import com.example.happygoaldemo.data.model.Calificacion
import com.example.happygoaldemo.data.model.AnnioMes
import java.util.*

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object PlaceholderContent {

    val apiService = RestApiService()

    /**
     * An array of sample (placeholder) items.
     */
    val ITEMS: MutableList<Calificacion> = ArrayList()

    /**
     * A map of sample (placeholder) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, Calificacion> = HashMap()

    private val COUNT = 25

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createPlaceholderItem(i))
        }
    }

    /*private fun fillListCalificaciones(){
        val calificaionParametros = AnnioMes(
                annio = 2021,
                mes = 1
        )
        *//*apiService.calificacionByUserFun("a@b.com", calificaionParametros, "") {
            if (it?.codeHttp == 200) {
                // it = newly added user parsed as response
                // it?.id = newly added user ID

                val action = LoginFragmentDirections.actionLoginFragmentToTestFragment()

            } else {
                //Timber.d("Error registering new user")
                //Toast.makeText(context, resources.getText(R.string.msjNoSeEncuentraRegistrado), Toast.LENGTH_LONG).show()
                Snackbar.make(view, resources.getText(R.string.msjNoSeEncuentraRegistrado), Snackbar.LENGTH_SHORT).show()
            }
        }*//*
    }*/

    private fun addItem(item: Calificacion) {
        ITEMS.add(item)
        ITEM_MAP.put(item.idCalificacion.toString(), item)
    }

    private fun createPlaceholderItem(position: Int): Calificacion {
        var data: Date = Calendar.getInstance().time
        return Calificacion(1, 2, makeDetails(position), data, "")
    }

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Item: ").append(position)
        for (i in 0..position - 1) {
            builder.append("\nMore details information here.")
        }
        return builder.toString()
    }

    /**
     * A placeholder item representing a piece of content.
     */
    /*data class Calificacion(val id: String, val content: String, val details: String) {
        override fun toString(): String = content
    }*/
}