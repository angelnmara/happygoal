package com.example.happygoaldemo.data.model

import java.util.*

data class Calificacion (
    val idCatCalificacion: Int?,
    val idUsuario: Int?,
    val idEmpresa: Int?,
    val emocion: String,
    val fechaCreacion:Date?
        )

data class CalificacionList(
        val calificacionList: List<Calificacion>
)