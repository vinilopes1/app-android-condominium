package com.example.vinicius.condominium.infra.api.endpoints

import com.example.vinicius.condominium.models.Comentario
import com.example.vinicius.condominium.models.Ocorrencia
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OcorrenciaEndPoint {

    @POST("ocorrencias/")
    fun postOcorrencia(@Body ocorrencia: Ocorrencia): Call<Ocorrencia>

    @GET("ocorrencias/{id}/")
    fun getOcorrencia(@Path("id") id: Int): Call<Ocorrencia>

    @POST("ocorrencias/{id}/comentarios/")
    fun postComentario(@Path("id") id: Int, @Body comentario: Comentario): Call<Comentario>

}
