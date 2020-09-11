package com.bomb.plus.gank.bean

import java.io.Serializable

data class GankTypeBean(
    val data: List<GankTypeData>,
    val status: Int
)


data class GankTypeData(
    val _id: String,
    val coverImageUrl: String,
    val desc: String,
    val title: String,
    val type: String
) : Serializable