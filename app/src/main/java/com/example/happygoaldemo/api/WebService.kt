package com.example.happygoaldemo.api

import com.example.happygoaldemo.data.model.Calificacion
import com.example.happygoaldemo.data.model.DrinkList
import retrofit2.http.*

interface WebService {
    @GET("search.php?s=")
    suspend fun getTragoByName(@Query("tragoName") tragoName:String) : DrinkList

    @Headers("Content-Type: application/json")
    @GET("calificacion-usuario")
    suspend fun getCalificacionByUser(@Query("idUsuario") idUser: String
                                      , @Query("annio") annio:Int?
                                      , @Query("mes") mes:Int?
                                      , @Header("Authorization") autHeader: String): List<Calificacion>

    @Headers("Content-Type: application/json")
    @GET("calificacion-month-year")
    suspend fun getCalificacionByMonthAnnio(@Query("mes") annio:Int?
                                            , @Query("annio") mes:Int?
                                            , @Header("Authorization") autHeader: String): List<Calificacion>

    @Headers("Content-Type: application/json")
    @GET("calificacion-fecha/{fecha}")
    suspend fun getCalificacionByDate(@Path("fecha") fecha: String
                                      , @Header("Authorization") autHeader: String): List<Calificacion>

    @Headers("Content-Type: application/json")
    @GET("calificacion/")
    suspend fun getCalificacion(@Header("Authorization") autHeader: String): List<Calificacion>


}