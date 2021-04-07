package com.bomb.plus.local_store.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.bomb.plus.local_store.db.entity.TestBean


@Dao
interface TestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(vararg item:TestBean)
}