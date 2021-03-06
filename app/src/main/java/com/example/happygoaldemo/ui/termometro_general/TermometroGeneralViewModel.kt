package com.example.happygoaldemo.ui.termometro_general

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.happygoaldemo.api.Repo
import com.example.happygoaldemo.tools.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class TermometroGeneralViewModel(private val repo:Repo):ViewModel() {

    var token: String = ""

    val fetchCalificacionList =
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                emit(
                    repo.getCalificacionList(token)
                )
            } catch (e: Exception) {
                Log.d("", e.toString())
                //emit(Resource.Failure(e))
            }
        }
}