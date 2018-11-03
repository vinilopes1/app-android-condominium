package com.example.vinicius.condominium.models

import com.google.gson.annotations.SerializedName
import java.sql.Time
import java.util.*

class Entrada(
        var descricao: String,
        @SerializedName("data") var data: String,
        @SerializedName("hora") var hora: String
    ) {

    @SerializedName("data_entrada") lateinit var dataEntrada: String
    @SerializedName("hora_entrada") lateinit var horaEntrada: String
    @SerializedName("informante") lateinit var informante: Perfil
    @SerializedName("status") lateinit var status: String

}