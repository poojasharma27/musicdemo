package com.musicdemo.di.modules

import com.musicdemo.network.ApiService
import com.musicdemo.ui.weather.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@Module
@InstallIn(ActivityComponent::class)
object RepositoryModule {
    @Provides
    fun provideWeatherRepo(apiService: ApiService) = WeatherRepository(apiService)

}