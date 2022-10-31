package com.vanapic.food.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vanapic.food.core.data.source.local.entity.FoodEntity

@Dao
interface FoodDao {
    @Query("SELECT * FROM food")
    fun getListFood(): LiveData<List<FoodEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFood(food: List<FoodEntity>)

    @Query("SELECT * FROM food WHERE name LIKE :searchQuery OR price LIKE :searchQuery")
    fun searchFood(searchQuery: String): LiveData<List<FoodEntity>>

    @Query("SELECT * FROM food where isFavorite = 1")
    fun getFavoriteFood(): LiveData<List<FoodEntity>>

    @Update
    fun updateFavoriteFood(food: FoodEntity)
}