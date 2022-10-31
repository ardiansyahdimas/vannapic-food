package com.vanapic.food.core.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vanapic.food.core.data.source.local.entity.BeverageEntity
import com.vanapic.food.core.data.source.local.entity.FoodEntity
import com.vanapic.food.core.data.source.local.entity.PromoEntity


@Database(
    entities = [
        FoodEntity::class,
        BeverageEntity::class,
        PromoEntity::class
    ],
    version = 4,
    exportSchema = false
)

abstract class VanapicDatabase : RoomDatabase() {

    abstract fun foodDao(): FoodDao
    abstract fun beverageDao(): BeverageDao
    abstract fun promoDao(): PromoDao

    companion object {
        @Volatile
        private var INSTANCE: VanapicDatabase? = null

        fun getInstance(context: Context): VanapicDatabase =
            INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VanapicDatabase::class.java,
                    "vanapic.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
    }
}