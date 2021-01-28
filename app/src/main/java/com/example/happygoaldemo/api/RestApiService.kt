package com.example.happygoaldemo.api

import com.example.happygoaldemo.data.model.Calificacion
import com.example.happygoaldemo.data.model.Login
import com.example.happygoaldemo.data.model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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