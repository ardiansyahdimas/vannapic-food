package com.vanapic.food.core.data.source.local

import androidx.lifecycle.LiveData
import com.vanapic.food.core.data.source.local.entity.FoodEntity
import com.vanapic.food.core.data.source.local.entity.PromoEntity
import com.vanapic.food.core.data.source.local.room.FoodDao
import com.vanapic.food.core.data.source.local.room.PromoDao

class PromoLocalDataSource private constructor(private val promoDao: PromoDao) {

    companion object {
        private var instance: PromoLocalDataSource? = null

        fun getInstance(promoDao: PromoDao): PromoLocalDataSource =
            instance ?: synchronized(this) {
                instance ?: PromoLocalDataSource(promoDao)
            }
    }

    fun getListPromo(): LiveData<List<PromoEntity>> = promoDao.getListPromo()
    fun insertPromo(promoList: List<PromoEntity>) = promoDao.insertPromo(promoList)

}