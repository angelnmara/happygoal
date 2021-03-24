package com.example.happygoaldemo.api

import com.example.happygoaldemo.data.model.Calificacion
import com.example.happygoaldemo.data.model.DetalleUsuarioResponse
import com.example.happygoaldemo.data.model.Login
import com.example.happygoaldemo.data.model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class RestApiService {
    fun loginFun(loginData: Login, onResult: (LoginResponse?) -> Unit){
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.addLogin(loginData).enqueue(
            object : Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    val loginResponse = LoginResponse(codeHttp = response.code()
                        , token = response.raw().headers().get("Authorization").toString())
                    onResult(loginResponse)
                }
            }
        )
    }

    fun detalleUsuario(userName:String, token: String, onResult: (DetalleUsuarioResponse?) -> Unit){
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)

        retrofit.getDetalleUsuario(userName, token).enqueue(
            object : Callback<DetalleUsuarioResponse> {
                override fun onFailure(call: Call<DetalleUsuarioResponse>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse(call: Call<DetalleUsuarioResponse>, response: Response<DetalleUsuarioResponse>) {
                    val statusCode = response.code().toInt()
                    var detalleUsuarioResponse:DetalleUsuarioResponse = response.body()!!
                    //(1, Date(), 1, Date(), 1, 1, 1, 1, 1)
                    onResult(detalleUsuarioResponse)
                }
            }
        )
    }

    fun calificacionFun(calificacion: Calificacion, token: String, onResult: (Int?) -> Unit){
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)

        retrofit.addCalificacion(calificacion, token).enqueue(
            object : Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    val statusCode = response.code().toInt()
                    onResult(statusCode)
                }
            }
        )
    }

    /*fun calificacionByUserFun(usuario:String, calificacionParametros: CalificacionParametros, token: String, onResult: (Calificacion) -> Unit){
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)

        retrofit.getCalificacionByUser(usuario, calificacionParametros, token).enqueue(
            object : Callback<Calificacion> {
                override fun onFailure(call: Call<Calificacion>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse(call: Call<Calificacion>, response: Response<Calificacion>) {
                    onResult(response.body())
                }
            }
        )
    }*/

}