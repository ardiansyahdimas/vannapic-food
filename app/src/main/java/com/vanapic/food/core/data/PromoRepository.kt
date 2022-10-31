package com.vanapic.food.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.vanapic.food.core.data.source.local.PromoLocalDataSource
import com.vanapic.food.core.data.source.remote.RemoteDataSource
import com.vanapic.food.core.data.source.remote.network.ApiResponse
import com.vanapic.food.core.data.source.remote.response.PromoResponse
import com.vanapic.food.core.domain.model.Promo
import com.vanapic.food.core.domain.repository.IPromoRepository
import com.vanapic.food.core.utils.AppExecutors
import com.vanapic.food.core.utils.PromoDataMapper

class PromoRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val promoLocalDataSource: PromoLocalDataSource,
    private val appExecutors: AppExecutors
) : IPromoRepository {

    companion object {
        @Volatile
        private var instance: PromoRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            promoLocalDataSource: PromoLocalDataSource,
            appExecutors: AppExecutors
        ): PromoRepository =
            instance ?: synchronized(this) {
                instance ?: PromoRepository(remoteData, promoLocalDataSource, appExecutors)
            }
    }

    override fun getListPromo (): LiveData<Resource<List<Promo>>> =
        object : NetworkBoundResource<List<Promo>, List<PromoResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<Promo>> {
                return Transformations.map(promoLocalDataSource.getListPromo()) {
                    PromoDataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Promo>?): Boolean =
//                data == null || data.isEmpty()
                true // ganti dengan true jika ingin selalu mengambil data dari internet

            override fun createCall(): LiveData<ApiResponse<List<PromoResponse>>> =
                remoteDataSource.getListPromo()

            override fun saveCallResult(data: List<PromoResponse>) {
                val promoList = PromoDataMapper.mapResponsesToEntities(data)
                promoLocalDataSource.insertPromo(promoList)
            }
        }.asLiveData()

}