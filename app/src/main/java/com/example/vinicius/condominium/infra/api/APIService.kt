package com.example.vinicius.condominium.infra.api

import com.example.vinicius.condominium.infra.api.endpoints.LoginEndPoint
import com.example.vinicius.condominium.infra.api.endpoints.PostEndPoint
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

public class APIService{

    private val BASE_URL = "http://172.16.183.118:8000/api/v1/"

    private lateinit var retrofit:Retrofit
    private lateinit var interceptorAPI: InterceptorAPI

    lateinit var loginEndPoint: LoginEndPoint
    lateinit var postEndPoint: PostEndPoint

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
        postEndPoint = this.retrofit.create(PostEndPoint::class.java)

    }
}