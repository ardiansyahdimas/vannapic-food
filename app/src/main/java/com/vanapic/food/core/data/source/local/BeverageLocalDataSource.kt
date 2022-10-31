package com.vanapic.food.core.data.source.local

import androidx.lifecycle.LiveData
import com.vanapic.food.core.data.source.local.entity.BeverageEntity
import com.vanapic.food.core.data.source.local.entity.FoodEntity
import com.vanapic.food.core.data.source.local.room.BeverageDao
import com.vanapic.food.core.data.source.local.room.FoodDao

class BeverageLocalDataSource private constructor(private val beverageDao: BeverageDao) {

    companion object {
        private var instance: BeverageLocalDataSource? = null

        fun getInstance(beverageDao: BeverageDao): BeverageLocalDataSource =
            instance ?: synchronized(this) {
                instance ?: BeverageLocalDataSource(beverageDao)
            }
    }

    fun getListBeverage(): LiveData<List<BeverageEntity>> = beverageDao.getListBeverage()
    fun insertBeverage(beverageList: List<BeverageEntity>) = beverageDao.insertBeverage(beverageList)
    fun getSearchBeverage(queryBeverage: String) = beverageDao.searchBeverage(queryBeverage)
    fun getFavoriteBeverage(): LiveData<List<BeverageEntity>> = beverageDao.getFavoriteBeverage()
    fun setFavoriteBeverage(beverage: BeverageEntity, newState: Boolean) {
        beverage.isFavorite = newState
        beverageDao.updateFavoriteBeverage(beverage)
    }


}