package com.example.vinicius.condominium.models

import com.google.gson.annotations.SerializedName

class Aviso(
    var descricao: String,
    var informante: Perfil,
    var prioridade: String
    ) {

    @SerializedName("id") var id: Long = 0

}