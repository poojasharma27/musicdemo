package com.musicdemo.ui.weather

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musicdemo.model.apientities.Weather
import com.musicdemo.network.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(var repository: WeatherRepository) : ViewModel() {

    private val TAG: String = "WeatherViewModel"
    var location = ObservableField("Noida")
    var weather = ObservableField<Weather>()

    var progressVisibility = ObservableField(false)


    private val _apiState = MutableStateFlow<ApiState>(ApiState.Empty)

    val apiState: StateFlow<ApiState> by lazy {
        _apiState
    }


    fun getWeatherByLocation() {

        viewModelScope.launch {
            repository.getWeatherByLocation(location.get() ?: "Chandigarh").onStart {
                _apiState.value = ApiState.Loading
                progressVisibility.set(true)
            }.catch { e ->
                progressVisibility.set(false)
                _apiState.value = ApiState.Failure(e)
            }.collect {
                progressVisibility.set(false)
                weather.set(it)
                _apiState.value = ApiState.Success(it)
            }
        }
    }

    fun updateLocation() {
        //location.set("Gurgaon")
    }
}