package com.example.happygoaldemo.api

import com.example.happygoaldemo.data.model.Calificacion
import com.example.happygoaldemo.data.model.CalificacionParametros
import com.example.happygoaldemo.data.model.Login
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.*

interface RestApi {
    @Headers("Content-Type: application/json")
    @POST("login")
    fun addLogin(@Body login: Login): Call<Void>

    @Headers("Content-Type: application/json")
    @POST("calificacion/")
    fun addCalificacion(@Body calificacion: Calificacion, @Header("Authorization") autHeader: String): Call<Void>

    @Headers("Content-Type: application/json")
    @GET("calificacion/")
    fun getCalificacionByUser(@Query("idUser") idUser: String, @Body calificacionParametros: CalificacionParametros, @Header("Authorization") autHeader: String): Call<Void>


}