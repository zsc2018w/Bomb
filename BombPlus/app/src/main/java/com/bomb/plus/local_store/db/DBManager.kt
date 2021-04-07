package com.bomb.plus.local_store.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bomb.plus.local_store.db.database.BombDataBase


object DBManager {

    private const val DB_name = "Bomb_data"

    private var bombDataBase: BombDataBase? = null

    fun getInstance(context: Context): BombDataBase? {

        return bombDataBase ?: synchronized(this) {
            bombDataBase ?: buildDataBase(context).also {
                bombDataBase = it
            }
        }

    }

    /**
     * 构建 DataBase
     */
    private fun buildDataBase(context: Context): BombDataBase? {
        return Room.databaseBuilder(context, BombDataBase::class.java, DB_name)
            //升级容错
            .fallbackToDestructiveMigration()
            //多进程
            .enableMultiInstanceInvalidation()
            //降级容错
            .fallbackToDestructiveMigrationOnDowngrade()
            //主线访问
            .allowMainThreadQueries()
            //版本升级
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
            .build()
    }


    /**
     * DataBase version update
     */
    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE IF NOT EXISTS 'test' ('id' INTEGER PRIMARY KEY autoincrement NOT NULL DEFAULT 0 , 'name' TEXT  NOT NULL  ,'tag' TEXT  NOT NULL )")
        }

    }

    private val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER  TABLE 'test' ADD COLUMN 'age' INTEGER NOT NULL  DEFAULT 0  ")
        }

    }


}