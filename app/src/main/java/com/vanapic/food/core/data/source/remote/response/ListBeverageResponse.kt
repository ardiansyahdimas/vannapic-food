package com.vanapic.food.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListBeverageResponse(

    @field:SerializedName("beverage")
    val beverage: List<BeverageResponse>
)