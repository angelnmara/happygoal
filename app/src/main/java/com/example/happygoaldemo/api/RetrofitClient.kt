package com.example.happygoaldemo.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val webservice by lazy{
        Retrofit.Builder()
            //.baseUrl("http://40.77.69.98:81")
            .baseUrl("http://10.0.2.2:81")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)
    }
}