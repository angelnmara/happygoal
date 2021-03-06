package com.example.happygoaldemo.tools

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.example.happygoaldemo.R
import com.example.happygoaldemo.data.model.Calificacion
import com.example.happygoaldemo.data.model.Emotion
import com.example.happygoaldemo.data.model.MesAnnioData
import com.example.happygoaldemo.data.model.GraphDataEmotion
import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AADataLabels
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAStyle
import com.github.aachartmodel.aainfographics.aatools.AAColor
import com.google.android.material.appbar.AppBarLayout
import java.text.DateFormatSymbols
import java.util.*
import java.util.Date.from


class Tools {

    val TAG = javaClass.name
    val mesDataList = arrayListOf<MesAnnioData>()
    val emotionList = arrayListOf<Emotion>()
    lateinit var listGraphEmotion:Array<Any>
    private var aaChartModel: AAChartModel? = null

    fun savePreferences(context: Context?, name: String, value: String, type: Int){
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)?: return
        with(sharedPref.edit()) {
            when(type){
                1 -> putString(name, value)
                2 -> putBoolean(name, value.toBoolean())
                3 -> putLong(name, value.toLong())
            }
            commit()
        }
    }

    fun configureGraph(listData:Array<Any>, view: View, titulo:String, subtitulo:String, descripcion:String){
        var tipo = 0
        when(tipo){
            0->{
                aaChartModel = customChart(listData, titulo, subtitulo, descripcion)
            }
            1->{
                aaChartModel = configurePieChart(listData, titulo, subtitulo, descripcion)
            }
            2->{
                aaChartModel = configurePyramidChart(listData, titulo, subtitulo, descripcion)
            }
            3->{
                aaChartModel = configureFunnelChart(listData, titulo, subtitulo, descripcion)
            }
            else->{
                aaChartModel = customChart(listData, titulo, subtitulo, descripcion)
            }
        }
        val aaChartView: AAChartView = view.findViewById(R.id.AAChartView)
        aaChartView.aa_drawChartWithChartModel(aaChartModel!!)
    }

    fun customChart(listData: Array<Any>, titulo: String, subtitulo: String, descripcion: String):AAChartModel{
        return AAChartModel()
            .chartType(AAChartType.Column)
            .backgroundColor("#FFFFFF")
            .dataLabelsEnabled(false)
            .yAxisGridLineWidth(0f)
            .legendEnabled(true)
            .touchEventEnabled(true)
            .title(titulo)
            .subtitle(subtitulo)
            .yAxisTitle("Emociones")
            .categories(arrayOf(
                "Feliz",
                "Motivado",
                "Tranquilo",
                "Estresado",
                "Enojado"
            ))
            .series(arrayOf(
                AASeriesElement()
                    .name(descripcion)
                    .data(listData)))
    }

    fun configureFunnelChart(listData: Array<Any>, titulo: String, subtitulo: String, descripcion: String): AAChartModel  {
        return AAChartModel()
            .chartType(AAChartType.Funnel)
            .title(titulo)
            .subtitle(subtitulo)
            .yAxisTitle("Emociones")
            .series(arrayOf(
                AASeriesElement()
                    .name(descripcion)
                    .dataLabels(
                        AADataLabels()
                        .enabled(true)
                        .inside(true)
                        .verticalAlign(AAChartVerticalAlignType.Middle)
                        .color(AAColor.Black)
                        .style(
                            AAStyle()
                            .fontSize(25f)
                            .textOutline("0px 0px contrast")))
                    .data(listData)))
    }

    fun configurePieChart(listData:Array<Any>, titulo:String, subtitulo:String, descripcion:String): AAChartModel  {
        return AAChartModel()
            .chartType(AAChartType.Pie)
            .backgroundColor("#ffffff")
            .title(titulo)
            .subtitle(subtitulo)
            .dataLabelsEnabled(true)//是否直接显示扇形图数据
            .yAxisTitle("℃")
            .series(arrayOf(
                AASeriesElement()
                    .name(descripcion)
                    .data(listData)))
    }

    fun configurePyramidChart(listData: Array<Any>, titulo: String, subtitulo: String, descripcion: String): AAChartModel  {
        return AAChartModel()
            .chartType(AAChartType.Pyramid)
            .title(titulo)
            .subtitle(subtitulo)
            .yAxisTitle("℃")
            .series(arrayOf(
                AASeriesElement()
                    .name(descripcion)
                    .data(listData)))
    }



    /*fun ocultaToolBar(activity: FragmentActivity?){
        val appBarLayout = activity?.findViewById<AppBarLayout>(R.id.app_bar_layout)
        val lp = appBarLayout?.layoutParams
        lp?.height = 0;
        appBarLayout?.layoutParams = lp
    }*/

    /*fun muestraToolBar(activity: FragmentActivity?){
        val appBarLayout = activity?.findViewById<AppBarLayout>(R.id.app_bar_layout)
        val lp = appBarLayout?.layoutParams
        val x: IntArray = intArrayOf(android.R.attr.actionBarSize)
        val attibutes = activity?.obtainStyledAttributes(x)
        lp?.height = attibutes?.getDimension(0, 1F)?.toInt()
        //.resources?.getInteger()?.toInt();
        appBarLayout?.layoutParams = lp
    }*/

    /*fun setDefaults(key: String?, value: String?, context: Context?) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()
        editor.putString(key, value)
        editor.commit()
    }*/

    fun getDefaultsString(key: String?, context: Context?): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getString(key, "")
    }

    fun getDefaultsBolean(key: String?, context: Context?): Boolean {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getBoolean(key, false)
    }

    fun getDefaultsLong(key: String?, context: Context?): Long {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getLong(key, 0L)
    }

    fun getDayOfWeekEspanniol(date: Date?, context: Context?):String?{
        //val calendar: Calendar = Calendar.getInstance()
        val c = Calendar.getInstance()
        c.time = date
        val dayOfWeek = c.get(Calendar.DAY_OF_WEEK)
        return when(dayOfWeek){
            Calendar.SUNDAY -> context?.getString(R.string.domingo)
            Calendar.MONDAY -> context?.getString(R.string.lunes)
            Calendar.TUESDAY -> context?.getString(R.string.martes)
            Calendar.WEDNESDAY -> context?.getString(R.string.miercoles)
            Calendar.THURSDAY -> context?.getString(R.string.jueves)
            Calendar.FRIDAY -> context?.getString(R.string.viernes)
            Calendar.SATURDAY -> context?.getString(R.string.sabado)
            else -> ""
        }
    }

    fun getDay(date: Date?):String{
        //val calendar: Calendar = Calendar.getInstance()
        val c = Calendar.getInstance()
        c.time = date
        return c.get(Calendar.DAY_OF_MONTH).toString()
    }

    fun getHora(date: Date?, context: Context?):String{
        //val calendar: Calendar = Calendar.getInstance()
        val c = Calendar.getInstance()
        c.time = date
        return c.get(Calendar.HOUR).toString().padStart(2, '0') + ":" + c.get(Calendar.MINUTE).toString().padStart(2, '0') + ":" + c.get(Calendar.SECOND).toString().padStart(2, '0') + " " + getAMPM(c.get(Calendar.AM_PM), context)
     }

    fun getAMPM(id: Int, context: Context?):String{
        return when(id){
            Calendar.AM -> context?.getString(R.string.am).toString()
            Calendar.PM -> context?.getString(R.string.pm).toString()
            else -> context?.getString(R.string.am).toString()
        }
    }

    fun getFaceResource(id: Int?):Int{
        return when(id){
            1 -> R.drawable.ic_feliz
            2 -> R.drawable.ic_motivado
            3 -> R.drawable.ic_tranquilo
            4 -> R.drawable.ic_estresado
            5 -> R.drawable.ic_enojado
            else -> R.drawable.ic_feliz
        }
    }

    fun fillEmotions(context:Context){
        var arrayPlanets = context.resources.getStringArray(R.array.emociones_array)
        var i = 1
        arrayPlanets.forEach {emocion ->
            val emotion = Emotion(
                idEmotion = i,
                nameEmotion = emocion
            )
            i++
            emotionList.add(emotion)
        }
    }

    fun fillCharts(data:List<Calificacion>, context: Context){
        if(emotionList.isEmpty()){
            fillEmotions(context)
        }
        var aoa= arrayListOf(arrayOf(String, Int))

        /*listGraphEmotion = arrayOf(
                arrayOf("Feliz",   14),
                arrayOf("Motivado", 14),
                arrayOf("Tranquilo", 15),
                arrayOf("Estresado", 19),
                arrayOf("Enojado", 20))

        listGraphEmotion.forEach {
            if(it.equals(arrayOf("Feliz", 0))){
                Log.d(TAG, "fillCharts: " + it.toString())
            }
        }*/

        emotionList.forEach { cal->
            //var graphDataEmotion = GraphDataEmotion(emotion = cal.nameEmotion, sumEmotion = data.filter { c -> c.calificacion == cal.idEmotion }.count())
            aoa.add(arrayOf(cal.nameEmotion, data.filter { c -> c.idCatCalificacion == cal.idEmotion }.count()))
        }
        aoa.removeAt(0)
            listGraphEmotion = aoa.toArray()


    }

    fun fillMesAnnioData(data: List<Calificacion>){
        val dfs = DateFormatSymbols(Locale("es"))
        val months = dfs.months
        data.forEach {
            val c: Calendar = Calendar.getInstance()
            c.setTime(it.fechaCreacion)
            val currentDate:Calendar = Calendar.getInstance()
            currentDate.add(Calendar.MONTH, -3)

            if(c.time>currentDate.time){
                val idmes = c.get(Calendar.MONTH)
                var annioMes = MesAnnioData(
                        idMes = idmes + 1,
                        nameMes = months[idmes].capitalize(),
                        annio = c.get(Calendar.YEAR),
                        anniomes = months[idmes].capitalize() + " - " + c.get(Calendar.YEAR),
                        activoMes = true
                )
                if(!mesDataList.contains(annioMes)){
                    mesDataList.add(annioMes)
                }
            }
        }
    }
}