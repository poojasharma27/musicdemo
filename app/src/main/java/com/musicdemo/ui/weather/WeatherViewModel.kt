package com.musicdemo.ui.weather

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musicdemo.model.apientities.Weather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(var repository: WeatherRepository) : ViewModel() {

    private val TAG: String = "WeatherViewModel"
    var location = ObservableField("Noida")
    var weather = ObservableField<Weather>()
    var time = ObservableField<String>("0.0")

    fun getWeatherByLocation() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getWeatherByLocation(location.get() ?: "Chandigarh")
                weather.set(response)
                Log.d(TAG, "getWeatherByLocation:  $response")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateLocation() {
        //location.set("Gurgaon")
    }
}