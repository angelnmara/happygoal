package com.example.happygoaldemo.ui.termometro_general

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.happygoaldemo.R
import com.example.happygoaldemo.api.RepoImpl
import com.example.happygoaldemo.data.model.DataSource
import com.example.happygoaldemo.tools.Resource
import com.example.happygoaldemo.tools.Tools
import com.example.happygoaldemo.tools.VMFactory
import com.example.happygoaldemo.ui.estadistica_personal_list.EstadisticaPersonalRecyclerViewAdapter

/**
 * A fragment representing a list of Items.
 */
class TermometroGeneralFragment : Fragment() {

    private val viewModel by viewModels<TermometroGeneralViewModel>{VMFactory(RepoImpl(DataSource()))}
    private var columnCount = 1
    //private lateinit var recyclerView: RecyclerView
    private var tools = Tools()
    private lateinit var tokenG:String
    private lateinit var progresBar:RelativeLayout
    private lateinit var clLeyendaTermometro:RelativeLayout
    private lateinit var listTermometro:RecyclerView
    private val TAG = javaClass.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tokenG = tools.getDefaultsString(getString(R.string.token), requireContext()).toString()
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_termometro_general_list, container, false)
        //recyclerView = view.findViewById<RecyclerView>(R.id.listTermometro)
        progresBar = view.findViewById(R.id.progressBar)
        clLeyendaTermometro = view.findViewById(R.id.clLeyendaTermometro)
        listTermometro = view.findViewById(R.id.listTermometro)

        // Set the adapter
        if (listTermometro is RecyclerView) {
            with(listTermometro) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                setupObserver(listTermometro)
                //adapter = MyItemRecyclerViewAdapter(PlaceholderContent.ITEMS)
            }
        }
        return view
    }

    private fun setupObserver(recyclerView: RecyclerView){
        viewModel.token = tokenG
        viewModel.fetchCalificacionList.observe(viewLifecycleOwner, Observer {
            result ->
            when(result){
                is Resource.Loading->{
                    progresBar.visibility = View.VISIBLE
                }
                is Resource.Success->{
                    progresBar.visibility = View.GONE
                    if(result.data.isEmpty()){
                        clLeyendaTermometro.visibility = View.VISIBLE
                        listTermometro.visibility = View.GONE
                    }else{
                        clLeyendaTermometro.visibility = View.GONE
                        listTermometro.visibility = View.VISIBLE
                        recyclerView.adapter = EstadisticaPersonalRecyclerViewAdapter(result.data, requireContext())
                    }
                }
                is Resource.Failure->{
                    listTermometro.visibility = View.GONE
                    clLeyendaTermometro.visibility = View.VISIBLE
                    Log.d(TAG, "setupObserver: " + result.exception.message)
                    Toast.makeText(requireContext(), result.exception.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            TermometroGeneralFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}