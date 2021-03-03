package com.example.happygoaldemo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatCheckedTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.happygoaldemo.adapters.CustomSpinnerAdapter
import com.example.happygoaldemo.api.RepoImpl
import com.example.happygoaldemo.data.model.*
import com.example.happygoaldemo.tools.Resource
import com.example.happygoaldemo.tools.Tools
import com.example.happygoaldemo.tools.VMFactory
import com.example.happygoaldemo.ui.estadistica_personal_list.EstadisticaPersonalRecyclerViewAdapter
import com.example.happygoaldemo.ui.estadistica_personal_list.EstadisticaPersonalViewModel
import kotlin.collections.ArrayList

/**
 * A fragment representing a list of Items.
 */
class EstadisticaPersonalListFragment : Fragment(), AdapterView.OnItemSelectedListener{

    private val viewModel by viewModels<EstadisticaPersonalViewModel> { VMFactory(RepoImpl(DataSource())) }
    private var columnCount = 1
    private lateinit var progressBar:RelativeLayout
    private lateinit var spinnerAdapter:CustomSpinnerAdapter
    private lateinit var rv:RecyclerView
    private lateinit var spinner:Spinner
    private var tools = Tools()
    private var TAG = javaClass.name
    private lateinit var userNameG: String
    private lateinit var tokenG: String
    

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

        userNameG = tools.getDefaultsString(getString(R.string.username), requireContext()).toString()

        tokenG = tools.getDefaultsString(getString(R.string.token), requireContext()).toString()

        spinner = view.findViewById(R.id.spnMonth)

        spinner.onItemSelectedListener = this

        rv = view.findViewById<RecyclerView>(R.id.list)
        progressBar = view.findViewById<RelativeLayout>(R.id.progressBar)

        // Set the adapter
        if (rv is RecyclerView) {
            with(rv) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }

                /*  inicializamos con null  */
                viewModel.setVariablesMutable(userNameG, null, null, tokenG)
                setupObserver(rv)
                //setupObseverText(rv)

            }
        }
        return view
    }

    private fun fillDDLMes(data:List<Calificacion>){
        tools.fillMesAnnioData(data)
        spinnerAdapter = CustomSpinnerAdapter(requireContext(), R.layout.custom_spinner_item, "anniomes", tools.mesDataList as ArrayList<Any>);
        spinner.adapter = spinnerAdapter
    }

    private fun setupObserver(reciclerView:RecyclerView){
        viewModel.fetchCalificacionList.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    Log.d("", "Loading")
                }
                is Resource.Success -> {
                    progressBar.visibility = View.GONE
                    reciclerView.adapter = EstadisticaPersonalRecyclerViewAdapter(result.data, requireContext())
                    Log.d(TAG, result.data.toString())

                    if(spinner.adapter==null){
                        fillDDLMes(result.data)
                    }
                }
                is Resource.Failure -> {
                    progressBar.visibility = View.GONE
                    Log.d("", "Failure")
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
            EstadisticaPersonalListFragment().apply {
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
            viewModel.setVariablesMutable(userNameG, mesAnnioData.get(0).annio, mesAnnioData.get(0).idMes, tokenG)
        }
        //viewModel.setMesMutable(2)
        //viewModel.setVariablesMutable(2021, 1, tokenG)

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}


/*var languages = arrayOf("Java", "PHP", "Kotlin", "Javascript", "Python", "Swift")*/

//ArrayAdapter<Drink>(requireContext(), android.R.layout.simple_spinner_dropdown_item, listD)

/*ArrayAdapter.createFromResource(
    requireContext(),
    languages,
    android.R.layout.simple_spinner_item
).also { adapter ->
    // Specify the layout to use when the list of choices appears
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    // Apply the adapter to the spinner
    spinner.adapter = adapter
}*/

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

/*private fun setupObseverText(reciclerView: RecyclerView){
        viewModel.fethPrueba.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    Log.d("", "Loading")
                }
                is Resource.Success -> {
                    progressBar.visibility = View.GONE
                    reciclerView.adapter = EstadisticaPersonalRecyclerViewAdapter(result.data, requireContext())
                    var annioMesList = arrayListOf<AnnioMes>()
                    result.data.forEach {
                        val c: Calendar = Calendar.getInstance()
                        c.setTime(it.fechaCreacion)
                        var annioMes = AnnioMes(
                                annio = c.get(Calendar.YEAR),
                                mes = c.get(Calendar.MONTH)
                        )
                        annioMesList.add(annioMes)
                    }
                    Log.d(TAG, annioMesList.toString())
                    spinnerAdapter = CustomSpinnerAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, "mesName", annioMesList.distinctBy { it.mes } as ArrayList<Any>);
                    spinner.adapter = spinnerAdapter
                    Log.d(TAG, result.data.toString())
                }
                is Resource.Failure -> {
                    progressBar.visibility = View.GONE
                    Log.d("", "Failure")
                }
            }
        })
    }*/

//viewModel.token = tools.getDefaultsString(getString(R.string.token), requireContext()).toString()
//viewModel.userName = tools.getDefaultsString(getString(R.string.username), requireContext()).toString()
/*parametersEstadisticaPersonal = ParametersEstadisticaPersonal(
    userName = userNameG,
    annio = null,
    mes = null,
    token = tokenG
)*/
//viewModel.setParametersEstadisticaPersonal(parametersEstadisticaPersonal)
