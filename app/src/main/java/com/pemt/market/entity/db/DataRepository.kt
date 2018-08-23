package com.pemt.market.entity.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.pemt.market.entity.Commodity

object DataRepository {
    private val commoditysLiveData by lazy {
        MediatorLiveData<List<Commodity>>()
                .apply {
                    addSource(AppDatabase.db.commodityDao().queryAll()) {
                        postValue(it)
                    }
                }
    }

    /**
     * Get the list of products from the database and get notified when the data changes.
     */
    fun getCommoditys(): LiveData<List<Commodity>> {
        return commoditysLiveData
    }

}