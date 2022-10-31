package com.vanapic.food.core.domain.usecase

import androidx.lifecycle.LiveData
import com.vanapic.food.core.data.BeverageRepository
import com.vanapic.food.core.data.FoodRepository
import com.vanapic.food.core.domain.model.Beverage
import com.vanapic.food.core.domain.model.Food

class BeverageInteractor(private val beverageRepository: BeverageRepository): BeverageUseCase {
    override fun getListBeverage() = beverageRepository.getListBeverage()
    override fun getSearchBeverage(search: String): LiveData<List<Beverage>> = beverageRepository.getSearchBeverage(search)
    override fun getFavoriteBeverage() = beverageRepository.getFavoriteBeverage()
    override fun setFavoriteBeverage(beverage: Beverage, state: Boolean) = beverageRepository.setFavoriteBeverage(beverage, state)
}