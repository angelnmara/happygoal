package com.example.happygoaldemo.ui.termometro_general

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.happygoaldemo.R
import com.example.happygoaldemo.api.RepoImpl
import com.example.happygoaldemo.data.model.DataSource
import com.example.happygoaldemo.tools.Resource
import com.example.happygoaldemo.tools.Tools
import com.example.happygoaldemo.tools.VMFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TermometroGeneralChartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TermometroGeneralChartFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val TAG = javaClass.name
    private var tools = Tools()
    private val viewModel by viewModels<TermometroGeneralChartViewModel> {VMFactory(RepoImpl(
        DataSource()
    ))  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_termometro_general_chart, container, false)
        viewModel.token = tools.getDefaultsString(getString(R.string.token), requireContext()).toString()
        setupObserver(view)
        return view
    }

    private fun setupObserver(view: View){
        viewModel.fetchCalificacion.observe(viewLifecycleOwner, Observer {
            result->
            when(result){
                is Resource.Loading->{
                    Log.d(TAG, "setupObserver: loading")
                }
                is Resource.Success->{
                    tools.fillCharts(result.data, requireContext())
                    tools.configureGraph(tools.listGraphEmotion, view, "Termometro General", "Últimos 3 meses", "Número de emociones")
                }
                is Resource.Failure->{
                    Log.d(TAG, "setupObserver: failure")
                }
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TermometroGeneralChartFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TermometroGeneralChartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}