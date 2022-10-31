package com.vanapic.food.ui.favorite

import androidx.lifecycle.ViewModel
import com.vanapic.food.core.domain.usecase.FoodUseCase

class FavoriteFoodViewModel(foodUseCase: FoodUseCase) : ViewModel() {
    val favoriteFood = foodUseCase.getFavoriteFood()
}