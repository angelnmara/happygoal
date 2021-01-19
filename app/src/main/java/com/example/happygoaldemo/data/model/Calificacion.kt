package com.example.happygoaldemo.data.model

import java.util.*

data class Calificacion (
    val idCalificacion: Int?,
    val calificacion: Int?,
    val emocion: String,
    val fechaCreacion: Date?,
    val idUsuario: String
        )

data class CalificacionList(
        val calificacionList: List<Calificacion>
)