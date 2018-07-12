package com.example.vinicius.condominium.infra.api

import com.example.vinicius.condominium.infra.api.endpoints.LoginEndPoint
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

public class APIService{

    private val BASE_URL = "http://172.20.10.8:8000/api/v1/"

    private lateinit var retrofit:Retrofit
    private lateinit var interceptorAPI: InterceptorAPI

    lateinit var loginEndPoint: LoginEndPoint

    constructor(Token: String){

        interceptorAPI = InterceptorAPI("Token " + Token)

        val builderUsuario = OkHttpClient.Builder()
        builderUsuario.addInterceptor(interceptorAPI)
        val usuario = builderUsuario.build()

        val builderRetrofit = Retrofit.Builder()
        retrofit = builderRetrofit
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(usuario)
                .build()

        loginEndPoint = this.retrofit.create(LoginEndPoint::class.java)

    }
}