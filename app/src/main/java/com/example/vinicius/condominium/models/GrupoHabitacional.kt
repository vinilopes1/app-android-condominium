package com.example.vinicius.condominium.models

import com.google.gson.annotations.SerializedName

class GrupoHabitacional(
        @SerializedName("id") var id: Int,
        @SerializedName("nome") var nome: String,
        @SerializedName("tipo_unidade") var tipoUnidade: String,
        @SerializedName("tipo") var tipoGrupoHabitacional: String
    ) {

}