package com.vanapic.food.ui.beverage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vanapic.food.core.domain.model.Beverage
import com.vanapic.food.core.domain.model.Food
import com.vanapic.food.core.domain.usecase.BeverageUseCase
import com.vanapic.food.core.domain.usecase.FoodUseCase

class BeverageViewModel(private val beverageUseCase: BeverageUseCase) : ViewModel() {
    val beverage = beverageUseCase.getListBeverage()
    fun getSearchBeverage(query: String) : LiveData<List<Beverage>> {
        return  beverageUseCase.getSearchBeverage(query)
    }
}