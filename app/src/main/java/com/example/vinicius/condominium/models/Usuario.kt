package com.example.vinicius.condominium.models

class Usuario(
    var password: String,
    var username: String){

    var id:Long = 0
    lateinit var email:String

}