package com.example.happygoaldemo.ui.estadistica_personal_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.happygoaldemo.api.Repo
import com.example.happygoaldemo.data.model.CalificacionParametros
import com.example.happygoaldemo.tools.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class MainViewModel(private val repo: Repo):ViewModel() {
    val fetchTragosList = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try{
            emit(repo.getTragosList("margarita"))
        }catch (e:Exception){
            Log.d("", e.toString())
            //emit(Resource.Failure(e))
        }
    }

    val fetchCalificacionList = liveData(Dispatchers.IO) {
        var calificacionParametros = CalificacionParametros(
            annio = 2021,
            mes = 1
        )
        emit(Resource.Loading())
        try{
            emit(repo.getCalificacionList("", calificacionParametros, ""))
        }catch (e:Exception){
            Log.d("", e.toString())
            //emit(Resource.Failure(e))
        }
    }

}