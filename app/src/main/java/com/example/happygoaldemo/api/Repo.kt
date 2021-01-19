package com.example.happygoaldemo.api

import com.example.happygoaldemo.data.model.Calificacion
import com.example.happygoaldemo.data.model.CalificacionParametros
import com.example.happygoaldemo.data.model.Drink
import com.example.happygoaldemo.tools.Resource

interface Repo {
    suspend fun getTragosList(tragoName:String): Resource<List<Drink>>
    suspend fun getCalificacionList(userName:String, calificacionParametros: CalificacionParametros, token:String): Resource<List<Calificacion>>
}