package com.example.happygoaldemo

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
import com.example.happygoaldemo.api.RepoImpl
import com.example.happygoaldemo.data.model.CalificacionParametros
import com.example.happygoaldemo.data.model.DataSource
import com.example.happygoaldemo.tools.Resource
import com.example.happygoaldemo.tools.ResourceString
import com.example.happygoaldemo.tools.Tools
import com.example.happygoaldemo.tools.VMFactory
import com.example.happygoaldemo.ui.estadistica_personal_list.EstadisticaPersonalRecyclerViewAdapter
import com.example.happygoaldemo.ui.estadistica_personal_list.EstadisticaPersonalViewModel

/**
 * A fragment representing a list of Items.
 */
class EstadisticaPersonalListFragment : Fragment(){

    //private val viewModel by viewModels<ViewModelPersonalList> { VMFactory(RepoImpl(DataSource())) }
    private val viewModel by viewModels<EstadisticaPersonalViewModel> { VMFactory(RepoImpl(DataSource())) }

    private var columnCount = 1

    private lateinit var progressBar:RelativeLayout

    private var tools = Tools()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =
            inflater.inflate(R.layout.fragment_estadistica_personal_list_list, container, false)

        var rv = view.findViewById<RecyclerView>(R.id.list)
        progressBar = view.findViewById<RelativeLayout>(R.id.progressBar)

        // Set the adapter
        if (rv is RecyclerView) {
            with(rv) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }

                /*viewModel.fetchTragosList.observe(viewLifecycleOwner, Observer { result ->
                    when (result) {
                        is Resource.Loading -> {
                            progressBar.visibility=View.VISIBLE
                            Log.d("",  "Loading")
                        }
                        is Resource.Success -> {
                            progressBar.visibility=View.GONE
                            //rv_tragos.adapter=MainAdapter(requireContext(), result.data, this)
                            Log.d("", result.data.toString() )
                            adapter = EstadisticaPersonalRecyclerViewAdapter(result.data)
                        }
                        is Resource.Failure -> {
                            progressBar.visibility=View.GONE
                            Toast.makeText(requireContext(), "Ocurrio un error al traer los datos ${result.exception}", Toast.LENGTH_SHORT).show()
                            Log.d("", "failure")
                        }
                    }
                })*/

                viewModel.token = tools.getDefaultsString(getString(R.string.token), requireContext()).toString()
                viewModel.userName = tools.getDefaultsString(getString(R.string.username), requireContext()).toString()
                viewModel.annio = 2021
                viewModel.mes = 1

                viewModel.fetchCalificacionList.observe(viewLifecycleOwner, Observer { result->
                    when(result){
                        is Resource.Loading->{
                            progressBar.visibility=View.VISIBLE
                            Log.d("", "Loading")
                        }
                        is Resource.Success->{
                            progressBar.visibility=View.GONE
                            adapter = EstadisticaPersonalRecyclerViewAdapter(result.data, requireContext())
                            Log.d("", result.data.toString())
                        }
                        is Resource.Failure->{
                            progressBar.visibility=View.GONE
                            Log.d("", "Failure")
                        }
                    }
                 })

            }
        }
        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            EstadisticaPersonalListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }

}