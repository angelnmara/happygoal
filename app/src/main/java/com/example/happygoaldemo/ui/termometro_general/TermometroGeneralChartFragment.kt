package com.example.happygoaldemo.ui.termometro_general

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatCheckedTextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.happygoaldemo.R
import com.example.happygoaldemo.adapters.CustomSpinnerAdapter
import com.example.happygoaldemo.api.RepoImpl
import com.example.happygoaldemo.data.model.Calificacion
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
class TermometroGeneralChartFragment : Fragment(), AdapterView.OnItemSelectedListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val TAG = javaClass.name
    private var tools = Tools()
    private lateinit var spinner: Spinner
    private lateinit var spinnerAdapter: CustomSpinnerAdapter
    private lateinit var token:String
    private val viewModel by viewModels<TermometroGeneralViewModel> {VMFactory(RepoImpl(
        DataSource()
    ))  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow)
        token = tools.getDefaultsString(getString(R.string.token), requireContext()).toString()
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
        //viewModel.token =
        viewModel.setVariablesMutables(null, null, token)
        spinner = view.findViewById(R.id.spnMonthChartTermometro)
        spinner.onItemSelectedListener = this
        setupObserver(view)
        return view
    }

    private fun setupObserver(view: View){
        viewModel.fetchCalificacionListMonthYear.observe(viewLifecycleOwner, Observer {
                result->
            when(result){
                is Resource.Loading->{
                    Log.d(TAG, "setupObserver: loading")
                }
                is Resource.Success->{
                    tools.fillCharts(result.data, requireContext())
                    tools.configureGraph(tools.listGraphEmotion, view, "Termometro General", "Últimos 3 meses", "Número de emociones")
                    if(spinner.adapter==null){
                        fillDDLMes(result.data)
                    }
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

    private fun fillDDLMes(data:List<Calificacion>){
        tools.fillMesAnnioData(data)
        spinnerAdapter = CustomSpinnerAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, "anniomes", tools.mesDataList as ArrayList<Any>);
        spinner.adapter = spinnerAdapter
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        var anniomes = (p1 as AppCompatCheckedTextView).text
        val mesAnnioData = tools.mesDataList.filter {
                c->c.anniomes == anniomes
        }
        viewModel.setVariablesMutables(mesAnnioData.get(0).idMes, mesAnnioData.get(0).annio, token)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}