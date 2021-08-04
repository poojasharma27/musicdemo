package com.musicdemo.ui.weather

import com.musicdemo.network.ApiService
import javax.inject.Inject

class WeatherRepository @Inject constructor(var apiService: ApiService)  {

    fun getWeatherByLocation () = apiService.getWeatherByLocation()

}