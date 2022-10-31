package com.vanapic.food.core.data.source.remote.network

import com.vanapic.food.core.data.source.remote.response.ListBeverageResponse
import com.vanapic.food.core.data.source.remote.response.ListFoodResponse
import com.vanapic.food.core.data.source.remote.response.ListPromoResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("food/list")
    fun getListFood(): Call<ListFoodResponse>

    @GET("beverage/list")
    fun getListBeverage(): Call<ListBeverageResponse>

    @GET("promo/list")
    fun getListPromo(): Call<ListPromoResponse>
}