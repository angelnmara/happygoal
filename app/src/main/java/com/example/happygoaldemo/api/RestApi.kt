package com.example.happygoaldemo.api

import com.example.happygoaldemo.data.model.*
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
    @GET("usuario-detalle-by-user-name/{userName}")
    fun getDetalleUsuario(@Path("userName") userName: String, @Header("Authorization") autHeader: String): Call<DetalleUsuarioResponse>

    @Headers("Content-Type: application/json")
    @GET("calificacion-usuario/")
    suspend fun getCalificacionByUser(@Query("idUser") idUser: String, @Body annioMes: AnnioMes, @Header("Authorization") autHeader: String): CalificacionList

}