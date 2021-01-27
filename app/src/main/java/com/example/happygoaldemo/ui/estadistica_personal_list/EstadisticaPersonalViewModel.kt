package com.example.happygoaldemo.ui.estadistica_personal_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.happygoaldemo.api.Repo
import com.example.happygoaldemo.data.model.CalificacionParametros
import com.example.happygoaldemo.tools.Resource
import com.example.happygoaldemo.tools.ResourceString
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class EstadisticaPersonalViewModel(private val repo: Repo):ViewModel() {

    lateinit var userName:String
    var annio:Int = 0
    var mes:Int = 0
    lateinit var token:String

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
        emit(Resource.Loading())
        try{
            emit(repo.getCalificacionList(userName
                    , annio
                    , mes
                    , token))
        }catch (e:Exception){
            Log.d("", e.toString())
            //emit(Resource.Failure(e))
        }
    }

}