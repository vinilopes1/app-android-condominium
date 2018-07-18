package com.example.vinicius.condominium.infra.api.endpoints

import com.example.vinicius.condominium.models.Entrada
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface EntradaEndPoint {

    @POST("entradas/")
    fun postEntrada(@Body entrada: Entrada): Call<Entrada>

}