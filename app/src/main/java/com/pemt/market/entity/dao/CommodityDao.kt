package com.pemt.market.entity.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.pemt.market.entity.Commodity
import com.pemt.market.entity.db.AppDatabase

@Dao
interface CommodityDao {

    companion object {
        val dao by lazy {
            AppDatabase.db.commodityDao()
        }
    }

    @Query("SELECT * FROM commodity")
    fun queryAll(): List<Commodity>


}