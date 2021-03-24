package com.example.happygoaldemo.api

import com.example.happygoaldemo.data.model.Calificacion
import com.example.happygoaldemo.data.model.Drink
import com.example.happygoaldemo.tools.Resource

interface Repo {
    suspend fun getTragosList(tragoName:String): Resource<List<Drink>>
    suspend fun getCalificacionList(token: String): Resource<List<Calificacion>>
    suspend fun getCalificacionList(idUsuario:Int
                                    , annio:Int?
                                    , mes:Int?
                                    , token:String): Resource<List<Calificacion>>
    suspend fun getCalificacionDate(fecha:String
                                    , idEmpresa: Int
                                    , token:String): Resource<List<Calificacion>>
    suspend fun getCalificacionByMonthAnnio(mes: Int?
                                            , annio: Int?
                                            , idEmpresa: Int
                                            , token: String):Resource<List<Calificacion>>
}