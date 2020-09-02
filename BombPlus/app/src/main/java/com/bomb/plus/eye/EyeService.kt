package com.bomb.plus.eye

import com.bomb.plus.core.UrlConstant
import com.bomb.plus.eye.bean.HomeBean
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface EyeService {

    @GET(UrlConstant.first_home_url)
    suspend fun requestFirstHomeData(@Query("num") num: Int): HomeBean

    @GET
    suspend fun requestHomeMoreData(@Url url: String): HomeBean

    @GET(UrlConstant.URL_RELATED_VIDEO)
    suspend fun getRelatedData(@Query("id") id: Long): HomeBean.Issue

}