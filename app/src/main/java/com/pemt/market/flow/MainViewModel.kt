package com.pemt.market.flow

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel
import com.pemt.market.entity.Commodity
import com.pemt.market.entity.db.AppDatabase
import kotlinx.coroutines.experimental.launch

class MainViewModel : ViewModel() {

    private val db by lazy {
        AppDatabase.db
    }

    val commodityLiveData by lazy {
        MediatorLiveData<List<Commodity>>()
                .apply {
                    addSource(db.commodityDao().queryAll(),
                            this::setValue)
                }
    }

    fun loadCommodity() {
        launch {

        }
    }

    fun saveCommodity(barcode: String) {
        launch {
            val commodity = db.commodityDao().queryByBarcode(barcode)
            if (null != commodity) {
                commodity.amount++
                db.commodityDao().update(commodity)
            } else {
                db.commodityDao().insert(
                        Commodity(name = "", amount = 1, barcode = barcode))
            }
        }
    }
}