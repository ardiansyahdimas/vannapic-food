package com.vanapic.food.core.domain.usecase

import androidx.lifecycle.LiveData
import com.vanapic.food.core.data.Resource
import com.vanapic.food.core.domain.model.Promo

interface PromoUseCase {
    fun getListPromo(): LiveData<Resource<List<Promo>>>
}