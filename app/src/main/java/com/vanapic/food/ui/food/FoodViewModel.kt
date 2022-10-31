package com.vanapic.food.ui.food

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vanapic.food.core.domain.model.Food
import com.vanapic.food.core.domain.usecase.FoodUseCase

class FoodViewModel(private val foodUseCase: FoodUseCase) : ViewModel() {
    val food = foodUseCase.getListFood()
    fun getSearchFood(query: String) : LiveData<List<Food>> {
        return  foodUseCase.getSearchFood(query)
    }
}