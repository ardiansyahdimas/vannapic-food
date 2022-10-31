package com.vanapic.food.core.domain.repository

import androidx.lifecycle.LiveData
import com.vanapic.food.core.data.Resource
import com.vanapic.food.core.domain.model.Beverage
import com.vanapic.food.core.domain.model.Food

interface IBeverageRepository {
    fun getListBeverage(): LiveData<Resource<List<Beverage>>>
    fun getSearchBeverage(search: String): LiveData<List<Beverage>>
    fun getFavoriteBeverage(): LiveData<List<Beverage>>
    fun setFavoriteBeverage(beverage: Beverage, state: Boolean)
}