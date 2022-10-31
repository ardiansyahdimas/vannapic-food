package com.vanapic.food.core.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vanapic.food.core.data.source.remote.network.ApiResponse
import com.vanapic.food.core.data.source.remote.network.ApiService
import com.vanapic.food.core.data.source.remote.response.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor(private val apiService: ApiService) {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(service: ApiService): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(service)
            }
    }


    fun getListFood(): LiveData<ApiResponse<List<FoodResponse>>> {
        val resultData = MutableLiveData<ApiResponse<List<FoodResponse>>>()
        val client = apiService.getListFood()
        client.enqueue(object : Callback<ListFoodResponse> {
            override fun onResponse(
                call: Call<ListFoodResponse>,
                response: Response<ListFoodResponse>
            ) {
                val dataArray = response.body()?.food
                resultData.value =
                    if (dataArray != null) ApiResponse.Success(dataArray) else ApiResponse.Empty
            }

            override fun onFailure(call: Call<ListFoodResponse>, t: Throwable) {
                resultData.value = ApiResponse.Error(t.message.toString())
                Log.e("RemoteDataSource", t.message.toString())
            }
        })
        return resultData
    }

    fun getListBeverage(): LiveData<ApiResponse<List<BeverageResponse>>> {
        val resultData = MutableLiveData<ApiResponse<List<BeverageResponse>>>()
        val client = apiService.getListBeverage()
        client.enqueue(object : Callback<ListBeverageResponse> {
            override fun onResponse(
                call: Call<ListBeverageResponse>,
                response: Response<ListBeverageResponse>
            ) {
                val dataArray = response.body()?.beverage
                resultData.value =
                    if (dataArray != null) ApiResponse.Success(dataArray) else ApiResponse.Empty
            }

            override fun onFailure(call: Call<ListBeverageResponse>, t: Throwable) {
                resultData.value = ApiResponse.Error(t.message.toString())
                Log.e("RemoteDataSource", t.message.toString())
            }
        })
        return resultData
    }

    fun getListPromo(): LiveData<ApiResponse<List<PromoResponse>>> {
        val resultData = MutableLiveData<ApiResponse<List<PromoResponse>>>()
        val client = apiService.getListPromo()
        client.enqueue(object : Callback<ListPromoResponse> {
            override fun onResponse(
                call: Call<ListPromoResponse>,
                response: Response<ListPromoResponse>
            ) {
                val dataArray = response.body()?.promo
                resultData.value =
                    if (dataArray != null) ApiResponse.Success(dataArray) else ApiResponse.Empty
            }

            override fun onFailure(call: Call<ListPromoResponse>, t: Throwable) {
                resultData.value = ApiResponse.Error(t.message.toString())
                Log.e("RemoteDataSource", t.message.toString())
            }
        })
        return resultData
    }
}