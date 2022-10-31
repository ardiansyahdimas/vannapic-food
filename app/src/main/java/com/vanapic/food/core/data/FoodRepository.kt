package com.vanapic.food.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import com.vanapic.food.core.data.source.local.FoodLocalDataSource
import com.vanapic.food.core.data.source.remote.RemoteDataSource
import com.vanapic.food.core.data.source.remote.network.ApiResponse
import com.vanapic.food.core.data.source.remote.response.FoodResponse
import com.vanapic.food.core.domain.model.Food
import com.vanapic.food.core.domain.repository.IFoodRepository
import com.vanapic.food.core.utils.AppExecutors
import com.vanapic.food.core.utils.FoodDataMapper

class FoodRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val foodLocalDataSource: FoodLocalDataSource,
    private val appExecutors: AppExecutors
) : IFoodRepository {

    companion object {
        @Volatile
        private var instance: FoodRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            foodLocalDataSource: FoodLocalDataSource,
            appExecutors: AppExecutors
        ): FoodRepository =
            instance ?: synchronized(this) {
                instance ?: FoodRepository(remoteData, foodLocalDataSource, appExecutors)
            }
    }

    override fun getListFood (): LiveData<Resource<List<Food>>> =
        object : NetworkBoundResource<List<Food>, List<FoodResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<Food>> {
                return Transformations.map(foodLocalDataSource.getListFood()) {
                    FoodDataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Food>?): Boolean =
//                data == null || data.isEmpty()
                true // ganti dengan true jika ingin selalu mengambil data dari internet

            override fun createCall(): LiveData<ApiResponse<List<FoodResponse>>> =
                remoteDataSource.getListFood()

            override fun saveCallResult(data: List<FoodResponse>) {
                val foodList = FoodDataMapper.mapResponsesToEntities(data)
                foodLocalDataSource.insertFood(foodList)
            }
        }.asLiveData()


    override fun getSearchFood(search: String): LiveData<List<Food>> {
        return foodLocalDataSource.getSearchFood(search).map {
            FoodDataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun getFavoriteFood(): LiveData<List<Food>> {
        return Transformations.map(foodLocalDataSource.getFavoriteFood()) {
            FoodDataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteFood(food: Food, state: Boolean) {
        val foodEntity = FoodDataMapper.mapDomainToEntity(food)
        appExecutors.diskIO().execute { foodLocalDataSource.setFavoriteFood(foodEntity, state) }
    }

}