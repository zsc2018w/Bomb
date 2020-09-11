package com.bomb.plus.gank

import com.bomb.plus.core.UrlConstant
import com.bomb.plus.gank.bean.GankBean
import com.bomb.plus.gank.bean.GankTypeBean
import com.bomb.plus.gank.bean.GirlTypeBean
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path


interface GankService {


    @GET(UrlConstant.URL_GANK_TYPE)
    suspend fun requestGankType(): GankTypeBean

    @GET(UrlConstant.URL_GIRL_TYPE)
    suspend fun requestDirlType(): GirlTypeBean

    @GET(UrlConstant.URL_GIRL_DATA)
    suspend fun requestGirlData(@Path("page") page: Int): GankBean


    @GET(UrlConstant.URL_ARTICLE_DATA)
    suspend fun requestAticleData(@Path("type") type: String, @Path("page") page: Int): GankBean

}