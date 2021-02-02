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

    fun setParametersEstadisticaPersonal(parametersEstadisticaPersonal: ParametersEstadisticaPersonal){
        parametersEstadisticaPersonalMutable.value = parametersEstadisticaPersonal
    }

    /*val fetchTragosList = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try{
            emit(repo.getTragosList("margarita"))
        }catch (e:Exception){
            Log.d("", e.toString())
            //emit(Resource.Failure(e))
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