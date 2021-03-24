package com.example.happygoaldemo.ui.comentarios_del_dia

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.happygoaldemo.api.Repo
import com.example.happygoaldemo.tools.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class ComentariosDelDiaViewModel(private val repo:Repo): ViewModel() {

    lateinit var fecha:String
    lateinit var token:String
    var idEmpresa:Int = 0

    val fetchListaByFecha = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try{
            emit(repo.getCalificacionDate(fecha
                , idEmpresa
                , token))
        }catch (e: Exception){
            Log.d("", e.toString())
            //emit(Resource.Failure(e))
        }
    }


}