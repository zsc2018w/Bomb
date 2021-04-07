package com.bomb.plus.local_store.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bomb.plus.local_store.db.entity.User

@Dao
interface UserDao {

    @Insert
    fun insertData(vararg user: User)

    /*  @Query(" UPDATE User SET userName=:user ")
      fun  updateData(zz:String,user:User)*/


    @Query("SELECT * FROM user ORDER BY isTop DESC ,time DESC")
    fun queryAll(): List<User>
}