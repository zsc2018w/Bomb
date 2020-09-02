package com.bomb.plus.gank

import com.bomb.plus.core.UrlConstant
import com.bomb.plus.gank.bean.GankGirlBean
import retrofit2.http.GET
import retrofit2.http.Path


interface GankService {

    @GET(UrlConstant.URL_GIRL_DATA)
    suspend fun requestGirlData(@Path("page") page: Int): GankGirlBean


}