package com.vanapic.food.ui.favorite

import androidx.lifecycle.ViewModel
import com.vanapic.food.core.domain.usecase.BeverageUseCase

class FavoriteBeverageViewModel(beverageUseCase: BeverageUseCase) : ViewModel() {
    val favoriteBeverage = beverageUseCase.getFavoriteBeverage()
}