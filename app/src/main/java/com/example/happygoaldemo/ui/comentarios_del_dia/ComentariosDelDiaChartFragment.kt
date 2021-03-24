package com.example.happygoaldemo.ui.comentarios_del_dia

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.example.happygoaldemo.R
import com.example.happygoaldemo.api.RepoImpl
import com.example.happygoaldemo.data.model.DataSource
import com.example.happygoaldemo.tools.Resource
import com.example.happygoaldemo.tools.Tools
import com.example.happygoaldemo.tools.VMFactory
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ComentariosDelDiaChartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ComentariosDelDiaChartFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val TAG = javaClass.name
    private var tools = Tools()
    private val viewModel by viewModels<ComentariosDelDiaViewModel> { VMFactory(RepoImpl(DataSource())) }

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
        val view:View = inflater.inflate(R.layout.fragment_comentarios_del_dia_chart, container, false)
        val cal = Calendar.getInstance()
        val dayOfMonth = cal[Calendar.DAY_OF_MONTH].toString().padStart(2, '0')
        val Month = (cal[Calendar.MONTH] + 1).toString().padStart(2, '0')
        val Year = cal[Calendar.YEAR].toString()
        viewModel.fecha = Year + Month + dayOfMonth
        viewModel.token = tools.getDefaultsString(getString(R.string.token), requireContext()).toString()
        viewModel.idEmpresa = tools.getDefaultsLong(getString(R.string.idempresa), requireContext()).toInt()
        setupObserver(view)
        return view
    }

    private fun setupObserver(view: View){
        viewModel.fetchListaByFecha.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                result->
            when(result){
                is Resource.Loading->{
                    Log.d(TAG, "setupObserver: loading")
                }
                is Resource.Success->{
                    tools.fillCharts(result.data, requireContext())
                    tools.configureGraph(tools.listGraphEmotion, view, "Comentarios del día", "Comentarios del día", "Número de emociones")
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
         * @return A new instance of fragment ComentariosDelDiaChartFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ComentariosDelDiaChartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}