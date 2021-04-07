package com.bomb.plus.local_store.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class User(
    var userName: String,
    var userAge: String,
    var isTop:Boolean,
    var time:Int

) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    override fun toString(): String {
        return "User(userName='$userName', userAge='$userAge', isTop=$isTop, time=$time, id=$id)"
    }


}