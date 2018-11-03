package com.example.vinicius.condominium.models

import com.google.gson.annotations.SerializedName

class Visitante(
        @SerializedName("id") var id: Long,
        @SerializedName("nome") var nome: String,
        @SerializedName("sexo") var sexo: String,
        @SerializedName("telefone") var telefone: String,
        @SerializedName("data_nascimento") var dataNascimento: String
    ){

}