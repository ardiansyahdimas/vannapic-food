package com.vanapic.food.core.domain.usecase

import androidx.lifecycle.LiveData
import com.vanapic.food.core.data.FoodRepository
import com.vanapic.food.core.data.Resource
import com.vanapic.food.core.domain.model.Food

class FoodInteractor(private val foodRepository: FoodRepository): FoodUseCase {
    override fun getListFood() = foodRepository.getListFood()
    override fun getSearchFood(search: String): LiveData<List<Food>> = foodRepository.getSearchFood(search)
    override fun getFavoriteFood() = foodRepository.getFavoriteFood()
    override fun setFavoriteFood(food: Food, state: Boolean) = foodRepository.setFavoriteFood(food, state)
}