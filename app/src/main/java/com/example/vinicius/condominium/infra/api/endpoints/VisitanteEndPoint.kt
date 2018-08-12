package com.example.vinicius.condominium.infra.api.endpoints

import com.example.vinicius.condominium.models.Visitante
import retrofit2.Call
import retrofit2.http.GET

interface VisitanteEndPoint {

    @GET("visitantes/")
    fun getVisitantes(): Call<MutableList<Visitante>>

}