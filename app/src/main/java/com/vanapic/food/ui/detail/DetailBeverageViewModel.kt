package com.vanapic.food.ui.detail

import androidx.lifecycle.ViewModel
import com.vanapic.food.core.domain.model.Beverage
import com.vanapic.food.core.domain.usecase.BeverageUseCase

class DetailBeverageViewModel(private val beverageUseCase: BeverageUseCase) : ViewModel() {
    fun setFavoriteBeverage(beverage: Beverage, newStatus:Boolean) =
        beverageUseCase.setFavoriteBeverage(beverage, newStatus)
}