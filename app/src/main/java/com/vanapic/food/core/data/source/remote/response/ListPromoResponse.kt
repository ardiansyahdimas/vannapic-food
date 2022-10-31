package com.vanapic.food.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListPromoResponse(

    @field:SerializedName("promo")
    val promo: List<PromoResponse>
)