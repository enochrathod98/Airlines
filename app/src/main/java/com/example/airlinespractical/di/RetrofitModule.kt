package com.example.airlinespractical.di

import com.example.airlinespractical.retrofit.AirlinesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RetrofitModule {
    @Singleton
    @Provides
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://general.63-141-249-130.plesk.page/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun getAirlinesAPI(retrofit: Retrofit): AirlinesApi {
        return retrofit.create(AirlinesApi::class.java)
    }
}