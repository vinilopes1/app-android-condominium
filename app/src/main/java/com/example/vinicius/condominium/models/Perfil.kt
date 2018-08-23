package com.example.vinicius.condominium.models

import com.google.gson.annotations.SerializedName

class Perfil (
        var usuario: Usuario,
        var nome: String,
        var sobrenome: String,
        var sexo: String,
        var telefone: String
    ) {

    @SerializedName("unidade_habitacional") lateinit var unidadeHabitacional: UnidadeHabitacional

}