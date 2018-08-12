package com.example.vinicius.condominium.utils

import android.content.Context

class MethodsUtils{

    lateinit private var securityPreferences: SecurityPreferences

    fun withContext(context: Context): MethodsUtils{
        securityPreferences = SecurityPreferences(context)
        return this
    }

    fun getToken(): String{
        return this.securityPreferences.getSavedString(CondomaisConstants.KEY.TOKEN_LOGADO)
    }

}