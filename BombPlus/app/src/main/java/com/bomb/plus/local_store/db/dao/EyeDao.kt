package com.bomb.plus.local_store.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.bomb.plus.local_store.db.entity.EyeDataBean


@Dao
interface EyeDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(itemList: List<EyeDataBean>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun add(vararg item: EyeDataBean)


}