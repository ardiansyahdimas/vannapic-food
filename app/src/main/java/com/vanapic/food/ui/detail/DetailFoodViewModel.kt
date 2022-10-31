package com.vanapic.food.ui.detail

import androidx.lifecycle.ViewModel
import com.vanapic.food.core.domain.model.Food
import com.vanapic.food.core.domain.usecase.FoodUseCase

class DetailFoodViewModel(private val foodUseCase: FoodUseCase) : ViewModel() {
    fun setFavoriteFood(food: Food, newStatus:Boolean) =
        foodUseCase.setFavoriteFood(food, newStatus)
}