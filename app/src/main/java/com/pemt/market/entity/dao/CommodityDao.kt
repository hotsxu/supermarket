package com.pemt.market.entity.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.pemt.market.entity.Commodity

@Dao
interface CommodityDao {

    @Query("SELECT * FROM commodity")
    fun queryAll(): LiveData<List<Commodity>>

    @Query("SELECT * FROM commodity WHERE barcode = :barcode")
    fun queryByBarcode(barcode: String): Commodity?

    @Update
    fun update(commodity: Commodity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(commodity: Commodity)
}