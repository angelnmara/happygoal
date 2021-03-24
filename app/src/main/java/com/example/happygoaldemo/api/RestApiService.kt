package com.example.happygoaldemo.api

import com.example.happygoaldemo.data.model.Calificacion
import com.example.happygoaldemo.data.model.Login
import com.example.happygoaldemo.data.model.LoginResponse
import com.example.happygoaldemo.data.model.UserDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.util.*

class RestApiService {
    suspend fun loginFun(loginData: Login, onResult: (LoginResponse?) -> Unit){
        try {
            val retrofit = ServiceBuilder.buildService(RestApi::class.java)
            retrofit.addLogin(loginData).enqueue(
                object : Callback<Void> {
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        onResult(null)
                    }
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        val loginResponse = LoginResponse(
                            codeHttp = response.code(),
                            token = response.raw().headers().get("Authorization").toString())
                        onResult(loginResponse)
                    }
                }
            )
        }catch (ex:Exception){
            var exeption:String? = ex.message
            onResult(null)
        }
    }

    suspend fun getUserDetails(userName:String, token: String, onResult: (UserDetailResponse?) -> Unit){
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.getUsersDetails(userName, token).enqueue(
            object :Callback<Void>{
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    val userDetailResponse = UserDetailResponse(
                        codeHttp = response.code(),
                        usuarioCreacion = 1,
                        fechaCreacion = Date(),
                        usuarioActualizacion = 1,
                        fechaActualizacion = Date(),
                        idUsuarioDetalle = 1,
                        idUsuario = 1,
                        idEmpresa = 1,
                        idArea = 1,
                        idRol = 1
                    )
                    onResult(userDetailResponse)
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