package com.vanapic.food.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "food", indices = [Index(value = ["id"], unique = true)])
data class FoodEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int ,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "photo")
    var photo: String,

    @ColumnInfo(name = "price")
    var price: Int,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "stock")
    var stock: Int,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
)