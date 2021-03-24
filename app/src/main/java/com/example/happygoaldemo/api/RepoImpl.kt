package com.example.happygoaldemo.api

import com.example.happygoaldemo.data.model.Calificacion
import com.example.happygoaldemo.data.model.DataSource
import com.example.happygoaldemo.data.model.Drink
import com.example.happygoaldemo.tools.Resource

class RepoImpl(private val dataSource: DataSource): Repo {
    override suspend fun getTragosList(tragoName:String): Resource<List<Drink>> {
        return dataSource.getTragoByName(tragoName)
    }

    override suspend fun getCalificacionList(token: String): Resource<List<Calificacion>> {
        return dataSource.getCalificacion(token)
    }

    override suspend fun getCalificacionList(
        idUsuario: Int,
        annio:Int?,
        mes:Int?,
        token: String
    ): Resource<List<Calificacion>> {
        return dataSource.getCalificacionByUser(idUsuario
            , annio
            , mes
            , token)
    }

    override suspend fun getCalificacionByMonthAnnio(mes: Int?, annio: Int?, idEmpresa: Int, token: String):Resource<List<Calificacion>>{
        return dataSource.getCalificacionByMonthAnnio(mes
            , annio
            , idEmpresa
            , token)
    }

    override suspend fun getCalificacionDate(
        fecha: String,
        idEmpresa: Int,
        token: String
    ): Resource<List<Calificacion>> {
        return dataSource.getCalificacionByDate(fecha
            , idEmpresa
            , token)
    }

}