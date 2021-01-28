package com.example.happygoaldemo.data.model

import java.lang.reflect.Constructor

data class AnnioMes (
    val annio: Int,
    val mes: Int,
    val mesName: String?
        ){
        constructor(annio: Int, mes: Int): this(annio, mes, when(mes){
                0->"Enero"
                1->"Febrero"
                2->"Marzo"
                3->"Abril"
                4->"Mayo"
                5->"Junio"
                6->"Julio"
                7->"Agosto"
                8->"Septiembre"
                9->"Octubre"
                10->"Noviembre"
                11->"Diciembre"
                else->""
        })
}

