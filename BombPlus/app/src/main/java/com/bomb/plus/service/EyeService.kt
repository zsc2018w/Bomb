package com.bomb.plus.service

import com.bomb.plus.bean.HomeBean
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface EyeService {

    @GET(EyeConstant.first_home_url)
    suspend fun requestFirstHomeData(@Query("num") num: Int): HomeBean

    @GET
    suspend fun requestHomeMoreData(@Url url: String): HomeBean
}