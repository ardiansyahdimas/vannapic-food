package com.vanapic.food.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import com.vanapic.food.core.data.source.local.BeverageLocalDataSource
import com.vanapic.food.core.data.source.local.FoodLocalDataSource
import com.vanapic.food.core.data.source.remote.RemoteDataSource
import com.vanapic.food.core.data.source.remote.network.ApiResponse
import com.vanapic.food.core.data.source.remote.response.BeverageResponse
import com.vanapic.food.core.data.source.remote.response.FoodResponse
import com.vanapic.food.core.domain.model.Beverage
import com.vanapic.food.core.domain.model.Food
import com.vanapic.food.core.domain.repository.IBeverageRepository
import com.vanapic.food.core.domain.repository.IFoodRepository
import com.vanapic.food.core.utils.AppExecutors
import com.vanapic.food.core.utils.BeverageDataMapper
import com.vanapic.food.core.utils.FoodDataMapper

class BeverageRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val beverageLocalDataSource: BeverageLocalDataSource,
    private val appExecutors: AppExecutors
) : IBeverageRepository {

    companion object {
        @Volatile
        private var instance: BeverageRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            beverageLocalDataSource: BeverageLocalDataSource,
            appExecutors: AppExecutors
        ): BeverageRepository =
            instance ?: synchronized(this) {
                instance ?: BeverageRepository(remoteData, beverageLocalDataSource, appExecutors)
            }
    }

    override fun getListBeverage (): LiveData<Resource<List<Beverage>>> =
        object : NetworkBoundResource<List<Beverage>, List<BeverageResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<Beverage>> {
                return Transformations.map(beverageLocalDataSource.getListBeverage()) {
                    BeverageDataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Beverage>?): Boolean =
//                data == null || data.isEmpty()
                true // ganti dengan true jika ingin selalu mengambil data dari internet

            override fun createCall(): LiveData<ApiResponse<List<BeverageResponse>>> =
                remoteDataSource.getListBeverage()

            override fun saveCallResult(data: List<BeverageResponse>) {
                val beverageList = BeverageDataMapper.mapResponsesToEntities(data)
                beverageLocalDataSource.insertBeverage(beverageList)
            }
        }.asLiveData()


    override fun getSearchBeverage(search: String): LiveData<List<Beverage>> {
        return beverageLocalDataSource.getSearchBeverage(search).map {
            BeverageDataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun getFavoriteBeverage(): LiveData<List<Beverage>> {
        return Transformations.map(beverageLocalDataSource.getFavoriteBeverage()) {
            BeverageDataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteBeverage(beverage: Beverage, state: Boolean) {
        val beverageEntity = BeverageDataMapper.mapDomainToEntity(beverage)
        appExecutors.diskIO().execute { beverageLocalDataSource.setFavoriteBeverage(beverageEntity, state) }
    }

}