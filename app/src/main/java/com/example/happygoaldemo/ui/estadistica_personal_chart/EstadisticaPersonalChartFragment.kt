package com.example.happygoaldemo.ui.estadistica_personal_chart

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.SpinnerAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatCheckedTextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.happygoaldemo.R
import com.example.happygoaldemo.adapters.CustomSpinnerAdapter
import com.example.happygoaldemo.api.RepoImpl
import com.example.happygoaldemo.data.model.Calificacion
import com.example.happygoaldemo.data.model.DataSource
import com.example.happygoaldemo.data.model.GraphDataEmotion
import com.example.happygoaldemo.tools.Resource
import com.example.happygoaldemo.tools.Tools
import com.example.happygoaldemo.tools.VMFactory
import com.example.happygoaldemo.ui.estadistica_personal_list.EstadisticaPersonalRecyclerViewAdapter
import com.example.happygoaldemo.ui.estadistica_personal_list.EstadisticaPersonalViewModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import java.util.Observer

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EstadisticaPersonalChartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EstadisticaPersonalChartFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private val TAG = javaClass.name
    private var tools = Tools()
    private lateinit var spinner: Spinner
    private lateinit var spinnerAdapter: CustomSpinnerAdapter
    private lateinit var userNameG: String
    private lateinit var tokenG: String
    private var idUsuario:Int = 0


    private val viewModel by viewModels<EstadisticaPersonalViewModel> { VMFactory(
        RepoImpl(
            DataSource()
        )
    ) }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow)
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
        val view:View = inflater.inflate(R.layout.fragment_estadistica_personal_chart, container, false)
        /*viewModel.userName = tools.getDefaultsString(getString(R.string.username), requireContext()).toString()
        viewModel.token = tools.getDefaultsString(getString(R.string.token), requireContext()).toString()*/
        userNameG = tools.getDefaultsString(getString(R.string.username), requireContext()).toString()
        tokenG = tools.getDefaultsString(getString(R.string.token), requireContext()).toString()
        idUsuario = tools.getDefaultsLong(getString(R.string.idusuario), requireContext()).toInt()
        viewModel.setVariablesMutable(idUsuario, null, null, tokenG)
        spinner = view.findViewById(R.id.spnMonthChart)
        spinner.onItemSelectedListener = this
        setupObserver(view)

        return view
    }



    /*arrayOf(
    arrayOf("Java",   67),
    arrayOf("Swift", 999),
    arrayOf("Python", 83),
    arrayOf("OC",     11),
    arrayOf("Go",     30)
    )*/
/*viewModel.userName = tools.getDefaultsString(getString(R.string.username), requireContext()).toString()
        viewModel.token = tools.getDefaultsString(getString(R.string.token), requireContext()).toString()*/
    private fun setupObserver(view: View){
        //tools.fillEmotions(requireContext())
        viewModel.fetchCalificacionList.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { result ->
                when (result) {
                    is Resource.Loading -> {
                        Log.d(TAG, "setupObserver: loading")
                    }
                    is Resource.Success -> {
                        tools.fillCharts(result.data, requireContext())
                        tools.configureGraph(tools.listGraphEmotion, view, "Estadística Personal", "Últimos tres meses", "Número de emociones")
                        Log.d(TAG, "setupObserver: success")
                        if(spinner.adapter==null){
                            fillDDLMes(result.data)
                        }
                    }
                    is Resource.Failure -> {
                        Log.d(TAG, "setupObserver: failure")
                    }
                }
            })
    }

    private fun fillDDLMes(data:List<Calificacion>){
        tools.fillMesAnnioData(data)
        spinnerAdapter = CustomSpinnerAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, "anniomes", tools.mesDataList as ArrayList<Any>);
        spinner.adapter = spinnerAdapter
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EstadisticaPersonalChartFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EstadisticaPersonalChartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if(p1!=null){
            var anniomes = (p1 as AppCompatCheckedTextView).text
            val mesAnnioData = tools.mesDataList.filter {
                    c->c.anniomes == anniomes
            }
            viewModel.setVariablesMutable(idUsuario, mesAnnioData.get(0).annio, mesAnnioData.get(0).idMes, tokenG)
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}