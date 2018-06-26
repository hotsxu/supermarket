package com.pemt.market.entity.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import com.pemt.market.app.MyApp.Companion.App
import com.pemt.market.entity.Commodity
import com.pemt.market.entity.dao.CommodityDao

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