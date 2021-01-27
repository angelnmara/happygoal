package com.example.happygoaldemo.api

import com.example.happygoaldemo.data.model.Calificacion
import com.example.happygoaldemo.data.model.CalificacionParametros
import com.example.happygoaldemo.data.model.DataSource
import com.example.happygoaldemo.data.model.Drink
import com.example.happygoaldemo.tools.Resource
import com.example.happygoaldemo.tools.ResourceString

class RepoImpl(private val dataSource: DataSource): Repo {
    override suspend fun getTragosList(tragoName:String): Resource<List<Drink>> {
        return dataSource.getTragoByName(tragoName)
    }

    override suspend fun getCalificacionList(
        userName: String,
        annio:Int,
        mes:Int,
        token: String
    ): Resource<List<Calificacion>> {
        return dataSource.getCalificacionByUser(userName
                , annio
                , mes
                , token)
    }
}