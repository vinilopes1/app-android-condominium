package com.example.vinicius.condominium.models

import com.google.gson.annotations.SerializedName
import java.io.File
import java.io.FileOutputStream

class Ocorrencia(
    var descricao: String,
    var localizacao: String,
    var publico: Boolean
    ) {

}