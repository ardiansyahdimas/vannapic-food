package com.vanapic.food.core.di

import android.content.Context
import com.vanapic.food.core.data.BeverageRepository
import com.vanapic.food.core.data.FoodRepository
import com.vanapic.food.core.data.PromoRepository
import com.vanapic.food.core.data.source.local.BeverageLocalDataSource
import com.vanapic.food.core.data.source.local.FoodLocalDataSource
import com.vanapic.food.core.data.source.local.PromoLocalDataSource
import com.vanapic.food.core.data.source.local.room.VanapicDatabase
import com.vanapic.food.core.data.source.remote.RemoteDataSource
import com.vanapic.food.core.data.source.remote.network.ApiConfig
import com.vanapic.food.core.domain.repository.IBeverageRepository
import com.vanapic.food.core.domain.repository.IFoodRepository
import com.vanapic.food.core.domain.repository.IPromoRepository
import com.vanapic.food.core.domain.usecase.*
import com.vanapic.food.core.utils.AppExecutors

object Injection {
    private fun provideFoodRepository(context: Context): IFoodRepository {
        val database = VanapicDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.provideApiService())
        val localDataSource = FoodLocalDataSource.getInstance(database.foodDao())
        val appExecutors = AppExecutors()

        return FoodRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }

    fun provideFoodUseCase(context: Context): FoodUseCase {
        val repository = provideFoodRepository(context)
        return FoodInteractor(repository as FoodRepository)
    }

    private fun provideBeverageRepository(context: Context): IBeverageRepository {
        val database = VanapicDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.provideApiService())
        val localDataSource = BeverageLocalDataSource.getInstance(database.beverageDao())
        val appExecutors = AppExecutors()

        return BeverageRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }

    fun provideBeverageUseCase(context: Context): BeverageUseCase {
        val repository = provideBeverageRepository(context)
        return BeverageInteractor(repository as BeverageRepository)
    }


    private fun providePromoRepository(context: Context): IPromoRepository {
        val database = VanapicDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.provideApiService())
        val localDataSource = PromoLocalDataSource.getInstance(database.promoDao())
        val appExecutors = AppExecutors()

        return PromoRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }

    fun providePromoUseCase(context: Context): PromoUseCase {
        val repository = providePromoRepository(context)
        return PromoInteractor(repository as PromoRepository)
    }
}