package com.bomb.plus.core

object UrlConstant {

    /**
     * eye
     * ----------------------------------------------------------------
     */
    private const val EYE_BASE_URL = "http://baobab.kaiyanapp.com/api/"
    const val first_home_url = "${EYE_BASE_URL}v2/feed?"
    const val URL_RELATED_VIDEO = "${EYE_BASE_URL}v4/video/related?"


    /**
     * https://gank.io/api
     * ----------------------------------------------------------------
     */
    private const val GANK_BASE_URL = "https://gank.io/api/v2"
    const val URL_GIRL_DATA = "${GANK_BASE_URL}data/category/Girl/type/Girl/page/{page}/count/10"

}