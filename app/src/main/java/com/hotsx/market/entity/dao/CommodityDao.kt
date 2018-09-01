package com.hotsx.market.entity.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hotsx.market.entity.Commodity

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