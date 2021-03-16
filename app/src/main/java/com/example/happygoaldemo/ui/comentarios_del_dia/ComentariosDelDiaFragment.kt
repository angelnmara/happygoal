package com.example.happygoaldemo.ui.comentarios_del_dia

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.happygoaldemo.R
import com.example.happygoaldemo.api.RepoImpl
import com.example.happygoaldemo.data.model.DataSource
import com.example.happygoaldemo.tools.Resource
import com.example.happygoaldemo.tools.Tools
import com.example.happygoaldemo.tools.VMFactory
import com.example.happygoaldemo.ui.estadistica_personal_list.EstadisticaPersonalRecyclerViewAdapter
import java.util.*

/**
 * A fragment representing a list of Items.
 */
class ComentariosDelDiaFragment : Fragment() {

    private val TAG = javaClass.name

    private val viewModel by viewModels<ComentariosDelDiaViewModel>{VMFactory(RepoImpl(DataSource()))}
    private var columnCount = 1
    private var tools = Tools()
    private lateinit var tokenG: String
    private lateinit var progressBar: RelativeLayout
    private lateinit var leyendaDatos:RelativeLayout
    private lateinit var rv:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_comentarios_del_dia_list, container, false)
        progressBar = view.findViewById<RelativeLayout>(R.id.progressBar)
        leyendaDatos= view.findViewById(R.id.clLeyendaDatos)

        tokenG = tools.getDefaultsString(getString(R.string.token), requireContext()).toString()

        rv = view.findViewById<RecyclerView>(R.id.listComentariosDia)

        // Set the adapter
        if (rv is RecyclerView) {
            with(rv) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                val cal = Calendar.getInstance()
                val dayOfMonth = cal[Calendar.DAY_OF_MONTH].toString().padStart(2, '0')
                val Month = (cal[Calendar.MONTH] + 1).toString().padStart(2, '0')
                val Year = cal[Calendar.YEAR].toString()
                viewModel.fecha = Year + Month + dayOfMonth
                viewModel.token = tokenG
                configureObserver(rv)
            }
        }
        return view
    }

    private fun configureObserver(reciclerView: RecyclerView){
        viewModel.fetchListaByFecha.observe(viewLifecycleOwner, androidx.lifecycle.Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    Log.d(TAG, "Loading")
                    progressBar.visibility = View.VISIBLE
                }
                is Resource.Failure -> {
                    Log.d(TAG, "Failure")
                    progressBar.visibility = View.GONE
                }
                is Resource.Success -> {
                    progressBar.visibility = View.GONE
                    if(result.data.count()>0){
                        reciclerView.adapter = EstadisticaPersonalRecyclerViewAdapter(result.data, requireContext())
                        leyendaDatos.visibility = View.GONE
                        rv.visibility = View.VISIBLE
                    }else{
                        rv.visibility = View.GONE
                        leyendaDatos.visibility = View.VISIBLE
                    }

                    Log.d(TAG, "Successs")
                }
            }
        }
        )
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
                ComentariosDelDiaFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }
}