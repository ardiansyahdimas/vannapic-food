package com.vanapic.food.core.utils

import com.vanapic.food.core.data.source.local.entity.PromoEntity
import com.vanapic.food.core.data.source.remote.response.PromoResponse
import com.vanapic.food.core.domain.model.Promo

object PromoDataMapper {
    fun mapResponsesToEntities(input: List<PromoResponse>): List<PromoEntity> {
        val promoList = ArrayList<PromoEntity>()
        input.map {
            val promo = PromoEntity(
                id = it.id,
                photo = it.photo,
                name = it.name,
                description = it.description,
                stock = it.stock,
                price = it.price,
                type = it.type
            )
            promoList.add(promo)
        }
        return promoList
    }

    fun mapEntitiesToDomain(input: List<PromoEntity>): List<Promo> =
        input.map {
            Promo(
                id = it.id,
                photo = it.photo,
                name = it.name,
                description = it.description,
                stock = it.stock,
                price = it.price,
                type = it.type
            )
        }

    fun mapDomainToEntity(input: Promo) = PromoEntity(
        id = input.id,
        photo = input.photo,
        name = input.name,
        description = input.description,
        stock = input.stock,
        price = input.price,
        type = input.type
    )
}