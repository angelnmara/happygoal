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
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatCheckedTextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.happygoaldemo.R
import com.example.happygoaldemo.adapters.CustomSpinnerAdapter
import com.example.happygoaldemo.api.RepoImpl
import com.example.happygoaldemo.data.model.Calificacion
import com.example.happygoaldemo.data.model.DataSource
import com.example.happygoaldemo.tools.Resource
import com.example.happygoaldemo.tools.Tools
import com.example.happygoaldemo.tools.VMFactory
import com.example.happygoaldemo.ui.estadistica_personal_list.EstadisticaPersonalRecyclerViewAdapter

/**
 * A fragment representing a list of Items.
 */
class TermometroGeneralFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private val viewModel by viewModels<TermometroGeneralViewModel>{VMFactory(RepoImpl(DataSource()))}
    private var columnCount = 1
    //private lateinit var recyclerView: RecyclerView
    private var tools = Tools()
    private lateinit var tokenG:String
    private lateinit var progresBar:RelativeLayout
    private lateinit var clLeyendaTermometro:RelativeLayout
    private lateinit var listTermometro:RecyclerView
    private val TAG = javaClass.name
    private lateinit var spinnerAdapter:CustomSpinnerAdapter
    private lateinit var spinner: Spinner
    private lateinit var linearLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow)
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
        spinner = view.findViewById(R.id.spnMonthTermometro)
        spinner.onItemSelectedListener = this
        viewModel.setVariablesMutables(null, null, tokenG)
        linearLayout = view.findViewById(R.id.lnlTermometro)

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

    private fun fillDDLMes(data:List<Calificacion>){
        tools.fillMesAnnioData(data)
        spinnerAdapter = CustomSpinnerAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, "anniomes", tools.mesDataList as ArrayList<Any>);
        spinner.adapter = spinnerAdapter
    }

    private fun setupObserver(recyclerView: RecyclerView){
        //viewModel.token = tokenG
        viewModel.fetchCalificacionListMonthYear.observe(viewLifecycleOwner, Observer {
            result ->
            when(result){
                is Resource.Loading->{
                    progresBar.visibility = View.VISIBLE
                }
                is Resource.Success->{
                    progresBar.visibility = View.GONE
                    if(result.data.isEmpty()){
                        clLeyendaTermometro.visibility = View.VISIBLE
                        linearLayout.visibility = View.GONE
                    }else{
                        clLeyendaTermometro.visibility = View.GONE
                        linearLayout.visibility = View.VISIBLE
                        recyclerView.adapter = EstadisticaPersonalRecyclerViewAdapter(result.data, requireContext())
                        if(spinner.adapter==null){
                            fillDDLMes(result.data)
                        }
                    }
                }
                is Resource.Failure->{
                    linearLayout.visibility = View.GONE
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

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if(p1!=null){
            var anniomes = (p1 as AppCompatCheckedTextView).text
            val mesAnnioData = tools.mesDataList.filter {
                    c->c.anniomes == anniomes
            }
            viewModel.setVariablesMutables(mesAnnioData.get(0).idMes, mesAnnioData.get(0).annio, tokenG)
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}