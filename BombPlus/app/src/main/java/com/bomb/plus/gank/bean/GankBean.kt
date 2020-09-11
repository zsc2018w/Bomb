package com.bomb.plus.gank.bean

data class GankBean(
    val `data`: List<GankDataBean>,
    val page: Int,
    val page_count: Int,
    val status: Int,
    val total_counts: Int,
    var refresh: Boolean
)