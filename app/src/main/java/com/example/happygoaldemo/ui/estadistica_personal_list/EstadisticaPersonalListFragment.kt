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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.happygoaldemo.api.RepoImpl
import com.example.happygoaldemo.data.model.DataSource
import com.example.happygoaldemo.data.model.Drink
import com.example.happygoaldemo.tools.Resource
import com.example.happygoaldemo.tools.VMFactory
import com.example.happygoaldemo.ui.estadistica_personal_list.MainViewModel

/**
 * A fragment representing a list of Items.
 */
class EstadisticaPersonalListFragment : Fragment(){

    //private val viewModel by viewModels<ViewModelPersonalList> { VMFactory(RepoImpl(DataSource())) }
    private val viewModel by viewModels<MainViewModel> { VMFactory(RepoImpl(DataSource())) }

    private var columnCount = 1

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

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }

                viewModel.fetchTragosList.observe(viewLifecycleOwner, Observer { result ->
                    when (result) {
                        is Resource.Loading -> {
                            //progressBar.visibility=View.VISIBLE
                            Log.d("",  "Loading")
                        }
                        is Resource.Success -> {
                            //progressBar.visibility=View.GONE
                            //rv_tragos.adapter=MainAdapter(requireContext(), result.data, this)
                            Log.d("", result.data.toString() )
                            adapter = MyItemRecyclerViewAdapter(result.data)
                        }
                        is Resource.Failure -> {
                            //progressBar.visibility=View.GONE
                            //Toast.makeText(requireContext(), "Ocurrio un error al traer los datos ${result.exception}", Toast.LENGTH_SHORT).show()
                            Log.d("", "failure")
                        }
                    }
                })
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*var tools = Tools()
        val token = tools.getDefaultsString(getString(R.string.token), requireContext()) //sharedPref.getString(getString(R.string.token), defaultValue)
        val userName = tools.getDefaultsString(getString(R.string.username), requireContext()) //sharedPref.getString(getString(R.string.username), defaultValue)

        if (token != null) {
            viewModel.token = token
        }
        if (userName != null) {
            viewModel.userName = userName
        }
        viewModel.calificacionParametros = CalificacionParametros(
                annio = 2021,
                mes = 1
        )
        viewModel.tragoName = "margarita"*/
        /*viewModel.fetchPersonalList.observe(viewLifecycleOwner, Observer { result->
            when(result){
                is Resource.Loading->{
                    Log.d("", "Loading")
                }
                is Resource.Success->{
                    Log.d("", "succes")
                }
                is Resource.Failure->{
                    Log.d("", result.exception.toString())
                }
            }
        })*/


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