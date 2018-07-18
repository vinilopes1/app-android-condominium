package com.example.vinicius.condominium.models

import com.google.gson.annotations.SerializedName

class Post(
        var descricao: String,
        @SerializedName("data_hora") var data: String,
        var tipo: String,
        var informante: Perfil
    ){

}