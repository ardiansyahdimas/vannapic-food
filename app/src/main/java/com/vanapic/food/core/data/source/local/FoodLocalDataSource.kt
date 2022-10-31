package com.vanapic.food.core.data.source.local

import androidx.lifecycle.LiveData
import com.vanapic.food.core.data.source.local.entity.FoodEntity
import com.vanapic.food.core.data.source.local.room.FoodDao

class FoodLocalDataSource private constructor(private val foodDao: FoodDao) {

    companion object {
        private var instance: FoodLocalDataSource? = null

        fun getInstance(foodDao: FoodDao): FoodLocalDataSource =
            instance ?: synchronized(this) {
                instance ?: FoodLocalDataSource(foodDao)
            }
    }

    fun getListFood(): LiveData<List<FoodEntity>> = foodDao.getListFood()
    fun insertFood(foodList: List<FoodEntity>) = foodDao.insertFood(foodList)
    fun getSearchFood(queryFood: String) = foodDao.searchFood(queryFood)
    fun getFavoriteFood(): LiveData<List<FoodEntity>> = foodDao.getFavoriteFood()
    fun setFavoriteFood(food: FoodEntity, newState: Boolean) {
        food.isFavorite = newState
        foodDao.updateFavoriteFood(food)
    }


}