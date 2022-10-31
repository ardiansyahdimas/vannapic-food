package com.vanapic.food.core.domain.repository

import androidx.lifecycle.LiveData
import com.vanapic.food.core.data.Resource
import com.vanapic.food.core.domain.model.Promo

interface IPromoRepository {
    fun getListPromo(): LiveData<Resource<List<Promo>>>
}