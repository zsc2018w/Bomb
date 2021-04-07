package com.bomb.plus.local_store.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bomb.plus.local_store.db.dao.EyeDao
import com.bomb.plus.local_store.db.dao.TestDao
import com.bomb.plus.local_store.db.entity.EyeDataBean
import com.bomb.plus.local_store.db.entity.TestBean
import com.bomb.plus.local_store.db.entity.User
import com.bomb.plus.local_store.db.dao.UserDao

@Database(
    entities = [User::class, EyeDataBean::class, TestBean::class],
    version = 3,
    exportSchema = false
)
abstract class BombDataBase : RoomDatabase() {

    abstract fun getUserDao(): UserDao?

    abstract fun getEyeDao(): EyeDao?

    abstract fun getTestDao(): TestDao?
}