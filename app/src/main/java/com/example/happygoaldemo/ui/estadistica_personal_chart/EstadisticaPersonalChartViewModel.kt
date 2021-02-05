package com.example.happygoaldemo.ui.estadistica_personal_chart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.happygoaldemo.api.Repo
import com.example.happygoaldemo.tools.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class EstadisticaPersonalChartViewModel(private val repo: Repo):ViewModel() {
    lateinit var userName:String
    lateinit var token:String
    val fetchCalificacion = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try{
            emit(repo.getCalificacionList(userName
                , null
                , null
                , token))
        }catch (e: Exception){
            Log.d("", e.toString())
            //emit(Resource.Failure(e))
        }
    }
}