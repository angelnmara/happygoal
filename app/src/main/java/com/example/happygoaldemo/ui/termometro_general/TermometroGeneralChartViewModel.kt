package com.example.happygoaldemo.ui.termometro_general

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.happygoaldemo.api.Repo
import com.example.happygoaldemo.tools.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class TermometroGeneralChartViewModel(private val repo: Repo): ViewModel() {
    private val TAG = javaClass.name
    lateinit var token:String
    val fetchCalificacion = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try{
            emit(repo.getCalificacionList(token))
        }catch (e: Exception){
            Log.d(TAG, e.toString())
            //emit(Resource.Failure(e))
        }
    }
}