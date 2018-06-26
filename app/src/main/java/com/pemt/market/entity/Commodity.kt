package com.pemt.market.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Commodity(@PrimaryKey var id: Int,
                     @ColumnInfo var name: String,
                     @ColumnInfo var amount: Int,
                     @ColumnInfo var barcode: String)