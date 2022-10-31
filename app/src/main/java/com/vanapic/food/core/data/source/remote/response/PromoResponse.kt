package com.vanapic.food.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class PromoResponse(

    @field:SerializedName("price")
    val price: Int ,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("photo")
    val photo: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("stock")
    val stock: Int,
    @field:SerializedName("type")
    val type: String
)