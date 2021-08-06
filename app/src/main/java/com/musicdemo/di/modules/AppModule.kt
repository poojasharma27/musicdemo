package com.musicdemo.di.modules

import com.musicdemo.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule{

    val baseUrl  = "http://api.weatherapi.com/"

    @Provides
    fun provideApiService() = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .build().create(ApiService::class.java)



}