package com.example.happygoaldemo.ui.estadistica_personal_chart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.happygoaldemo.R
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EstadisticaPersonalChartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EstadisticaPersonalChartFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var aaChartModel: AAChartModel? = null

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
        val view:View = inflater.inflate(R.layout.fragment_estadistica_personal_chart, container, false)
        aaChartModel = configurePieChart()
        val aaChartView: AAChartView = view.findViewById(R.id.AAChartView)
        aaChartView.aa_drawChartWithChartModel(aaChartModel!!)
        return view
    }

    fun configurePieChart(): AAChartModel  {
        return AAChartModel()
            .chartType(AAChartType.Pie)
            .backgroundColor("#ffffff")
            .title("LANGUAGE MARKET SHARES JANUARY,2020 TO MAY")
            .subtitle("virtual data")
            .dataLabelsEnabled(true)//是否直接显示扇形图数据
            .yAxisTitle("℃")
            .series(arrayOf(
                AASeriesElement()
                    .name("Language market shares")
                    .data(arrayOf(
                        arrayOf("Java",   67),
                        arrayOf("Swift", 999),
                        arrayOf("Python", 83),
                        arrayOf("OC",     11),
                        arrayOf("Go",     30)
                    ))))
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
}