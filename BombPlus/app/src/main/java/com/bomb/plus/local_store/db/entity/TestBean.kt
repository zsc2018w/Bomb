package com.bomb.plus.local_store.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "test")
class TestBean(
    val name: String,
    val tag: String,
    val age: Int
) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}