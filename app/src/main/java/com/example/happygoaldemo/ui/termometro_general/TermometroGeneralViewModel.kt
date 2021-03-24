package com.example.happygoaldemo.ui.termometro_general

import android.util.Log
import androidx.lifecycle.*
import com.example.happygoaldemo.api.Repo
import com.example.happygoaldemo.data.model.ParametersTermometroGeneral
import com.example.happygoaldemo.tools.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class TermometroGeneralViewModel(private val repo:Repo):ViewModel() {
    private val TAG = javaClass.name

    /*var token: String = ""
    var mes: Int = 0
    var annio: Int = 2020*/

    private val parametersTermometroGeneralMutable = MutableLiveData<ParametersTermometroGeneral>()

    fun setVariablesMutables(mes:Int?, annio:Int?, token:String){
        val parametersTermometroGeneral = ParametersTermometroGeneral(
            mes = mes, annio = annio, token = token
        )
        parametersTermometroGeneralMutable.value = parametersTermometroGeneral
    }

    val fetchCalificacionList =
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                emit(
                    repo.getCalificacionList(parametersTermometroGeneralMutable.value!!.token)
                )
            } catch (e: Exception) {
                Log.d("", e.toString())
                //emit(Resource.Failure(e))
            }
        }

    val fetchCalificacionListMonthYear = parametersTermometroGeneralMutable.distinctUntilChanged().switchMap {
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                emit(
                    repo.getCalificacionByMonthAnnio(parametersTermometroGeneralMutable.value!!.mes,
                        parametersTermometroGeneralMutable.value!!.annio,
                        parametersTermometroGeneralMutable.value!!.token)
                )
            } catch (e: Exception) {
                Log.d("", e.toString())
                //emit(Resource.Failure(e))
            }
        }
    }
}