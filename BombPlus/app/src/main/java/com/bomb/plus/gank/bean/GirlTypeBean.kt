package com.bomb.plus.gank.bean

data class GirlTypeBean(
    val data: List<GirlTypeData>,
    val status: Int
)

data class GirlTypeData(
    val _id: String,
    val coverImageUrl: String,
    val desc: String,
    val title: String,
    val type: String
)