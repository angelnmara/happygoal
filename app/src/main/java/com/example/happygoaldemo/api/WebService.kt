package com.example.happygoaldemo.api

import com.example.happygoaldemo.data.model.Calificacion
import com.example.happygoaldemo.data.model.CalificacionList
import com.example.happygoaldemo.data.model.CalificacionParametros
import com.example.happygoaldemo.data.model.DrinkList
import retrofit2.http.*

interface WebService {
    @GET("search.php?s=")
    suspend fun getTragoByName(@Query("tragoName") tragoName:String) : DrinkList

    @Headers("Content-Type: application/json")
    @GET("calificacion-usuario")
    suspend fun getCalificacionByUser(@Query("idUsuario") idUser: String
                                      , @Query("annio") annio:Int
                                      , @Query("mes") mes:Int
                                      , @Header("Authorization") autHeader: String): List<Calificacion>
}