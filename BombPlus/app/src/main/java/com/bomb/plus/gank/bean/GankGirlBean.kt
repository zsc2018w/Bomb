package com.bomb.plus.gank.bean

data class GankGirlBean(
    val data: List<GirlDataBean>,
    val page: Int,
    val page_count: Int,
    val status: Int,
    val total_counts: Int
)