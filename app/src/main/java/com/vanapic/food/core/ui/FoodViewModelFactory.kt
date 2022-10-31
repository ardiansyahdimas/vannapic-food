package com.vanapic.food.core.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vanapic.food.core.di.Injection
import com.vanapic.food.core.domain.usecase.FoodUseCase
import com.vanapic.food.ui.detail.DetailFoodViewModel
import com.vanapic.food.ui.favorite.FavoriteFoodViewModel
import com.vanapic.food.ui.food.FoodViewModel

class FoodViewModelFactory private constructor(private val foodUseCase: FoodUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: FoodViewModelFactory? = null

        fun getInstance(context: Context): FoodViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: FoodViewModelFactory(
                    Injection.provideFoodUseCase(context)
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FoodViewModel::class.java) -> {
                FoodViewModel(foodUseCase) as T
            }
            modelClass.isAssignableFrom(FavoriteFoodViewModel::class.java) -> {
                FavoriteFoodViewModel(foodUseCase) as T
            }
            modelClass.isAssignableFrom(DetailFoodViewModel::class.java) -> {
                DetailFoodViewModel(foodUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}