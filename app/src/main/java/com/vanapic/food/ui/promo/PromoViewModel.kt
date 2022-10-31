package com.vanapic.food.ui.promo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vanapic.food.core.domain.model.Food
import com.vanapic.food.core.domain.usecase.FoodUseCase
import com.vanapic.food.core.domain.usecase.PromoUseCase

class PromoViewModel(private val promoUseCase: PromoUseCase) : ViewModel() {
    val promo = promoUseCase.getListPromo()
}