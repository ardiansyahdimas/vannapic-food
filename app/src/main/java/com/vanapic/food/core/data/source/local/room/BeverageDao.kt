package com.vanapic.food.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vanapic.food.core.data.source.local.entity.BeverageEntity
import com.vanapic.food.core.data.source.local.entity.FoodEntity

@Dao
interface BeverageDao {
    @Query("SELECT * FROM beverage")
    fun getListBeverage(): LiveData<List<BeverageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBeverage(beverage: List<BeverageEntity>)

    @Query("SELECT * FROM beverage WHERE name LIKE :searchQuery OR price LIKE :searchQuery")
    fun searchBeverage(searchQuery: String): LiveData<List<BeverageEntity>>

    @Query("SELECT * FROM beverage where isFavorite = 1")
    fun getFavoriteBeverage(): LiveData<List<BeverageEntity>>

    @Update
    fun updateFavoriteBeverage(beverage: BeverageEntity)
}