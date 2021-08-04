package com.musicdemo.ui.weather

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(var repository: WeatherRepository) : ViewModel() {


    fun getWeatherByLocation() = repository.getWeatherByLocation()

}