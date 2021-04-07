package com.bomb.plus.local_store.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "eye_data")
class EyeDataBean(
    val text: String,
    val title: String,
    val description: String,
    val providerIcon: String,
    val category: String,
    val icon: String,
    val name: String,
    val feed: String,
    val detail: String,
    val blurred: String,
    val playUrl: String,
    val duration: Long,
    val message: String,
    val createTime: Long,
    val tags: String
) {

    @PrimaryKey(autoGenerate = false)
    var id: Long = 0L


}