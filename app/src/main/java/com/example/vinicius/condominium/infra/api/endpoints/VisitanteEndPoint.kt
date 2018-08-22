package com.example.vinicius.condominium.infra.api.endpoints

import com.example.vinicius.condominium.models.Visitante
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface VisitanteEndPoint {

    @GET("visitantes/")
    fun getVisitantes(): Call<MutableList<Visitante>>

    @POST("visitantes/")
    fun postVisitante(@Body visitante: Visitante): Call<Visitante>

}