package com.vanapic.food.core.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vanapic.food.core.di.Injection
import com.vanapic.food.core.domain.usecase.BeverageUseCase
import com.vanapic.food.ui.beverage.BeverageViewModel
import com.vanapic.food.ui.detail.DetailBeverageViewModel
import com.vanapic.food.ui.favorite.FavoriteBeverageViewModel
import com.vanapic.food.ui.favorite.FavoriteFoodViewModel

class BeverageViewModelFactory private constructor(private val beverageUseCase: BeverageUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: BeverageViewModelFactory? = null

        fun getInstance(context: Context): BeverageViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: BeverageViewModelFactory(
                    Injection.provideBeverageUseCase(context)
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(BeverageViewModel::class.java) -> {
                BeverageViewModel(beverageUseCase) as T
            }
            modelClass.isAssignableFrom(FavoriteBeverageViewModel::class.java) -> {
                FavoriteBeverageViewModel(beverageUseCase) as T
            }
            modelClass.isAssignableFrom(DetailBeverageViewModel::class.java) -> {
                DetailBeverageViewModel(beverageUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}