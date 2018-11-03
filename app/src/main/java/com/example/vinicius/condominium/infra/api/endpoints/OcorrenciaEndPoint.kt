package com.example.vinicius.condominium.infra.api.endpoints

import com.example.vinicius.condominium.models.Comentario
import com.example.vinicius.condominium.models.Ocorrencia
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface OcorrenciaEndPoint {

    @Multipart
    @POST("ocorrencias/")
    fun postOcorrencia(@Part image: MultipartBody.Part, @Part("foto") ocorrencia: Ocorrencia): Call<Ocorrencia>

    @GET("ocorrencias/{id}/")
    fun getOcorrencia(@Path("id") id: Int): Call<Ocorrencia>

    @POST("ocorrencias/{id}/comentarios/")
    fun postComentario(@Path("id") id: Int, @Body comentario: Comentario): Call<Comentario>

}
