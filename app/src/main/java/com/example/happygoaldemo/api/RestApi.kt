package com.example.happygoaldemo.api

import com.example.happygoaldemo.data.model.Calificacion
import com.example.happygoaldemo.data.model.Login
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface RestApi {
    @Headers("Content-Type: application/json")
    @POST("login")
    fun addLogin(@Body login: Login): Call<Void>

    @Headers("Content-Type: application/json")
    @POST("calificacion/")
    fun addCalificacion(@Body calificacion: Calificacion, @Header("Authorization") autHeader: String): Call<Void>


}