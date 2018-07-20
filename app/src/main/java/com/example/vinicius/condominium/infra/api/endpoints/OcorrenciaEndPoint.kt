package com.example.vinicius.condominium.infra.api.endpoints

import com.example.vinicius.condominium.models.Ocorrencia
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface OcorrenciaEndPoint {

    @POST("ocorrencias/")
    fun postOcorrencia(@Body ocorrencia: Ocorrencia): Call<Ocorrencia>

}