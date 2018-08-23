package com.example.vinicius.condominium.models

import com.google.gson.annotations.SerializedName
import java.io.File
import java.io.FileOutputStream

class Ocorrencia(
    var descricao: String,
    var localizacao: String,
    var publico: Boolean
    ) {

    @SerializedName("status") lateinit var status: String
    @SerializedName("informante") lateinit var informante: Perfil
    @SerializedName("foto") lateinit var foto: String
    @SerializedName("comentarios") lateinit var comentarios: MutableList<Comentario>

}