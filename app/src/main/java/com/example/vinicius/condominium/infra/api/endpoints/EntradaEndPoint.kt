package com.example.vinicius.condominium.infra.api.endpoints

import com.example.vinicius.condominium.models.Detail
import com.example.vinicius.condominium.models.Entrada
import com.example.vinicius.condominium.models.Visitante
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EntradaEndPoint {

    @POST("entradas/")
    fun postEntrada(@Body entrada: Entrada): Call<Entrada>

    @GET("entradas/{id}/")
    fun getEntradaDetalhe(@Path("id") id: Int ): Call<Entrada>

    @GET("entradas/{id}/cancelar")
    fun cancelarEntrada(@Path("id") id: Int ): Call<Detail>
}