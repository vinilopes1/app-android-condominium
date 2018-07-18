package com.example.vinicius.condominium.infra.api.endpoints

import com.example.vinicius.condominium.models.Aviso
import retrofit2.Call
import retrofit2.http.GET

interface AvisoEndPoint {

    @GET("avisos/")
    fun getAvisos(): Call<MutableList<Aviso>>

}