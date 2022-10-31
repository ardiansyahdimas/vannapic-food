package com.vanapic.food.core.domain.usecase

import com.vanapic.food.core.data.PromoRepository

class PromoInteractor(private val promoRepository: PromoRepository): PromoUseCase {
    override fun getListPromo() = promoRepository.getListPromo()
}