package com.hotsx.market.entity.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hotsx.market.app.MyApp.Companion.App
import com.hotsx.market.entity.Commodity
import com.hotsx.market.entity.dao.CommodityDao

@Database(entities = [Commodity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        val db by lazy {
            Room.databaseBuilder(App,
                    AppDatabase::class.java, "market.db").build()
        }
    }

    abstract fun commodityDao(): CommodityDao
}