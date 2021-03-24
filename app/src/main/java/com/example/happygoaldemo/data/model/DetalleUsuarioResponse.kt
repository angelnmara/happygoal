package com.example.happygoaldemo.data.model

import java.util.*

data class DetalleUsuarioResponse(
    var usuarioCreacion: Int,
    var fechaCreacion: Date,
    var usuarioActualizacion: Int,
    var fechaActualizacion: Date,
    var idUsuarioDetalle: Int,
    var idUsuario: Int,
    var idEmpresa: Int,
    var idArea: Int,
    var idRol: Int
)
