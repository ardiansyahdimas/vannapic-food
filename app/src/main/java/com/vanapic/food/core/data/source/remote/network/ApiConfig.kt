package com.vanapic.food.core.data.source.remote.network

import com.google.gson.GsonBuilder
import cz.msebera.httpclient.android.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object ApiConfig {
    private val interceptorBuild = if(BuildConfig.DEBUG) {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    } else {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    }

    private fun provideOkHttpClient(): OkHttpClient {
        val interceptor = TokenInterceptor()
        return OkHttpClient.Builder()
            .addInterceptor(interceptorBuild)
            .addInterceptor(interceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    var gson = GsonBuilder()
        .setLenient()
        .create()

    fun provideApiService(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://j3dvy.mocklab.io/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(provideOkHttpClient())
            .build()
        return retrofit.create(ApiService::class.java)
    }

    class TokenInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val newRequest: Request = chain.request().newBuilder()
                .build()
            return chain.proceed(newRequest)
        }
    }
}