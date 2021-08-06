package com.musicdemo.ui.weather

import com.musicdemo.Api
import com.musicdemo.network.ApiService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepository @Inject constructor(var apiService: ApiService)  {

    suspend fun getWeatherByLocation (location: String) = apiService.getWeatherByLocation(Api.KEY.value,location,"no")


}