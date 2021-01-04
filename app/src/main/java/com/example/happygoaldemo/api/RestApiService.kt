package com.example.happygoaldemo.api

import android.content.Context
import android.provider.Settings.Global.getString
import com.example.happygoaldemo.R
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

}