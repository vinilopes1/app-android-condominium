package com.example.vinicius.condominium.infra.api.endpoints

import com.example.vinicius.condominium.models.TokenAPIModel
import com.example.vinicius.condominium.models.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginEndPoint{

    @POST("token/")
    fun logarAPI(@Body usuario: Usuario): Call<TokenAPIModel>

}