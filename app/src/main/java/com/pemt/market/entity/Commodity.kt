package com.pemt.market.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Commodity(@PrimaryKey var id: Int? = null,
                     @ColumnInfo var group: String = "",
                     @ColumnInfo var area: Int,
                     @ColumnInfo var name: String,
                     @ColumnInfo var amount: Int,
                     @ColumnInfo var barcode: String)