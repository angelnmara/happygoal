package com.example.happygoaldemo.ui.estadistica_personal_list

import android.util.Log
import androidx.lifecycle.*
import com.example.happygoaldemo.api.Repo
import com.example.happygoaldemo.data.model.ParametersEstadisticaPersonal
import com.example.happygoaldemo.tools.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class EstadisticaPersonalViewModel(private val repo: Repo):ViewModel() {

    /*lateinit var userName:String
    var annio:Int? = null
    var mes:Int? = null
    lateinit var token:String*/

    private val parametersEstadisticaPersonalMutable = MutableLiveData<ParametersEstadisticaPersonal>()
    private val mesMutable = MutableLiveData<Int>()
    private val annioMutable = MutableLiveData<Int>()

    fun setMesMutable(mes:Int){
        mesMutable.value = mes
    }

    fun setVariablesMutable(userName:String, annio:Int?, mes:Int?, token:String){
        val parametersEstadisticaPersonal = ParametersEstadisticaPersonal(
                userName = userName,
                annio = annio,
                mes = mes,
                token = token
        )
        parametersEstadisticaPersonalMutable.value = parametersEstadisticaPersonal
    }

    /*fun setParametersEstadisticaPersonal(parametersEstadisticaPersonal: ParametersEstadisticaPersonal){
        parametersEstadisticaPersonalMutable.value = parametersEstadisticaPersonal
    }*/




    /*val fethPrueba = mesMutable.distinctUntilChanged().switchMap {mes ->
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try{
                emit(repo.getCalificacionList("a@b.com"
                        , 2021
                        , mes
                        , tokenG))
            }catch (e:Exception){
                Log.d("", e.toString())
                //emit(Resource.Failure(e))
            }
        }
    }*/

        val fetchCalificacionList = parametersEstadisticaPersonalMutable.distinctUntilChanged().switchMap {
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try{
                emit(repo.getCalificacionList(it.userName
                        , it.annio
                        , it.mes
                        , it.token))
            }catch (e:Exception){
                Log.d("", e.toString())
                //emit(Resource.Failure(e))
            }
        }
    }

}