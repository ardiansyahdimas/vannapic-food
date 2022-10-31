package com.vanapic.food.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListFoodResponse(

	@field:SerializedName("food")
	val food: List<FoodResponse>
)