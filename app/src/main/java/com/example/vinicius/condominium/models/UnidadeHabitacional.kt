package com.example.vinicius.condominium.models

import com.google.gson.annotations.SerializedName

class UnidadeHabitacional(
        @SerializedName("id") var id: Int,
        @SerializedName("nome") var nome: String,
        @SerializedName("grupo_habitacional") var grupoHabitacional: GrupoHabitacional
    ){
}