package com.vanapic.food.core.domain.usecase

import androidx.lifecycle.LiveData
import com.vanapic.food.core.data.Resource
import com.vanapic.food.core.domain.model.Food

interface FoodUseCase {
    fun getListFood(): LiveData<Resource<List<Food>>>
    fun getSearchFood(search: String): LiveData<List<Food>>
    fun getFavoriteFood(): LiveData<List<Food>>
    fun setFavoriteFood(food: Food, state: Boolean)
}