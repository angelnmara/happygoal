package com.example.happygoaldemo.data.model

import java.util.*

data class UserDetailResponse(
    val codeHttp: Int,
    val usuarioCreacion: Int,
    val fechaCreacion: Date,
    val usuarioActualizacion: Int,
    val fechaActualizacion: Date,
    val idUsuarioDetalle: Int,
    val idUsuario:Int,
    val idEmpresa:Int,
    val idArea:Int,
    val idRol:Int,

)
