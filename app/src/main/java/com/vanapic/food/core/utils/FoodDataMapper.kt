package com.vanapic.food.core.utils

import com.vanapic.food.core.data.source.local.entity.FoodEntity
import com.vanapic.food.core.data.source.remote.response.FoodResponse
import com.vanapic.food.core.domain.model.Food

object FoodDataMapper {
    fun mapResponsesToEntities(input: List<FoodResponse>): List<FoodEntity> {
        val foodList = ArrayList<FoodEntity>()
        input.map {
            val food = FoodEntity(
                id = it.id,
                photo = it.photo,
                name = it.name,
                description = it.description,
                stock = it.stock,
                price = it.price,
                isFavorite = false
            )
            foodList.add(food)
        }
        return foodList
    }

    fun mapEntitiesToDomain(input: List<FoodEntity>): List<Food> =
        input.map {
            Food(
                id = it.id,
                photo = it.photo,
                name = it.name,
                description = it.description,
                stock = it.stock,
                price = it.price,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: Food) = FoodEntity(
        id = input.id,
        photo = input.photo,
        name = input.name,
        description = input.description,
        stock = input.stock,
        price = input.price,
        isFavorite = input.isFavorite
    )
}