package com.example.vinicius.condominium.models

import com.google.gson.annotations.SerializedName

class Post(
        @SerializedName("descricao") var descricao: String,
        @SerializedName("atualizado_em_data_br") var data: String,
        @SerializedName("atualizado_em_hora_br") var hora: String,
        @SerializedName("tipo") var tipo: String,
        @SerializedName("status_post") var status: String,
        @SerializedName("informante") var informante: Perfil
    ){

}