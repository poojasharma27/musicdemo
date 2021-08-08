package com.musicdemo.ui.weather

import com.musicdemo.Api
import com.musicdemo.model.apientities.Weather
import com.musicdemo.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherRepository @Inject constructor(var apiService: ApiService)  {

    suspend fun getWeatherByLocation (location: String) =
        flow<Weather> {
            emit( apiService.getWeatherByLocation(Api.KEY.value,location,"no"))
        }.flowOn(Dispatchers.IO)




}