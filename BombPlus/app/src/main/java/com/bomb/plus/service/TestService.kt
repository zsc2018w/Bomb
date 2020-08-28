package com.bomb.plus.service


import retrofit2.http.GET


interface TestService {

    @GET("http://baobab.kaiyanapp.com/api/v4/categories/all")
    suspend fun getOne():String
}