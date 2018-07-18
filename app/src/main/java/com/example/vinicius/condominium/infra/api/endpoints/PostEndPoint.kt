package com.example.vinicius.condominium.infra.api.endpoints

import com.example.vinicius.condominium.models.Post
import retrofit2.Call
import retrofit2.http.GET

interface PostEndPoint {

    @GET("posts/")
    fun getPosts(): Call<MutableList<Post>>

}