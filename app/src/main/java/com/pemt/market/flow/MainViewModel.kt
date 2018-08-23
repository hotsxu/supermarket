package com.pemt.market.flow

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
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
                        Commodity(area = 0, name = "", amount = 1, barcode = barcode))
            }
        }
    }

    fun updateCommodity(commodity: Commodity) {
        launch {
            db.commodityDao().update(commodity)
        }
    }
}