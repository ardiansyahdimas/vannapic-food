package com.vanapic.food.core.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vanapic.food.core.di.Injection
import com.vanapic.food.core.domain.model.Promo
import com.vanapic.food.core.domain.usecase.FoodUseCase
import com.vanapic.food.core.domain.usecase.PromoUseCase
import com.vanapic.food.ui.detail.DetailFoodViewModel
import com.vanapic.food.ui.favorite.FavoriteFoodViewModel
import com.vanapic.food.ui.food.FoodViewModel
import com.vanapic.food.ui.promo.PromoViewModel

class PromoViewModelFactory private constructor(private val promoUseCase: PromoUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: PromoViewModelFactory? = null

        fun getInstance(context: Context): PromoViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: PromoViewModelFactory(
                    Injection.providePromoUseCase(context)
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(PromoViewModel::class.java) -> {
                PromoViewModel(promoUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}