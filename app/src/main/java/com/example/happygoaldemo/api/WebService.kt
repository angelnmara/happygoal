package com.example.happygoaldemo.api

import com.example.happygoaldemo.data.model.Calificacion
import com.example.happygoaldemo.data.model.DrinkList
import retrofit2.http.*

interface WebService {
    @GET("search.php?s=")
    suspend fun getTragoByName(@Query("tragoName") tragoName:String) : DrinkList

    @Headers("Content-Type: application/json")
    @GET("calificacion-usuario")
    suspend fun getCalificacionByUser(@Query("idUsuario") idUsuario: Int
                                      , @Query("annio") annio:Int?
                                      , @Query("mes") mes:Int?
                                      , @Header("Authorization") autHeader: String): List<Calificacion>

    @Headers("Content-Type: application/json")
    @GET("calificacion-month-year")
    suspend fun getCalificacionByMonthAnnio(@Query("mes") annio:Int?
                                            , @Query("annio") mes:Int?
                                            , @Query("idEmpresa") idEmpresa:Int
                                            , @Header("Authorization") autHeader: String): List<Calificacion>

    @Headers("Content-Type: application/json")
    @GET("calificacion-fecha/{fecha}")
    suspend fun getCalificacionByDate(@Path("fecha") fecha: String
                                      , @Header("Authorization") autHeader: String): List<Calificacion>

    @Headers("Content-Type: application/json")
    @GET("calificacion-empresa")
    suspend fun getCalificacionByFechaEmpresa(@Query("fechaStr") fechaStr: String
                                           , @Query("idEmpresa") idEmpresa: Int
                                      , @Header("Authorization") autHeader: String): List<Calificacion>

    @Headers("Content-Type: application/json")
    @GET("calificacion/")
    suspend fun getCalificacion(@Header("Authorization") autHeader: String): List<Calificacion>


}