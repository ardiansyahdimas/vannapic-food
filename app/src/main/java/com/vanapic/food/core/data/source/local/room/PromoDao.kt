package com.vanapic.food.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vanapic.food.core.data.source.local.entity.FoodEntity
import com.vanapic.food.core.data.source.local.entity.PromoEntity

@Dao
interface PromoDao {
    @Query("SELECT * FROM promo")
    fun getListPromo(): LiveData<List<PromoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPromo(promo: List<PromoEntity>)

}