package com.vanapic.food.core.utils

import com.vanapic.food.core.data.source.local.entity.BeverageEntity
import com.vanapic.food.core.data.source.remote.response.BeverageResponse
import com.vanapic.food.core.domain.model.Beverage

object BeverageDataMapper {
    fun mapResponsesToEntities(input: List<BeverageResponse>): List<BeverageEntity> {
        val beverageList = ArrayList<BeverageEntity>()
        input.map {
            val beverage = BeverageEntity(
                id = it.id,
                photo = it.photo,
                name = it.name,
                description = it.description,
                stock = it.stock,
                price = it.price,
                isFavorite = false
            )
            beverageList.add(beverage)
        }
        return beverageList
    }

    fun mapEntitiesToDomain(input: List<BeverageEntity>): List<Beverage> =
        input.map {
            Beverage(
                id = it.id,
                photo = it.photo,
                name = it.name,
                description = it.description,
                stock = it.stock,
                price = it.price,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: Beverage) = BeverageEntity(
        id = input.id,
        photo = input.photo,
        name = input.name,
        description = input.description,
        stock = input.stock,
        price = input.price,
        isFavorite = input.isFavorite
    )
}