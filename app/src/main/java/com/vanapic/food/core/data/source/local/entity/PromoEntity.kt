package com.vanapic.food.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "promo", indices = [Index(value = ["id"], unique = true)])
data class PromoEntity(
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
    @ColumnInfo(name = "type")
    var type: String,
)