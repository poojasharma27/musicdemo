package com.musicdemo.ui.weather.details

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.musicdemo.model.apientities.Weather
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherDetailsViewModel  @Inject constructor(
    stateHandle: SavedStateHandle
): ViewModel() {

    private val TAG: String = "WeatherDetailsViewModel"
    val weather = ObservableField<Weather>()

    init {
        val data = stateHandle.get<Weather>("data")
        //Log.d(TAG, "Data received :  $data")
        weather.set(data)

        //You can also call an api using an id from last screen/activity
        //eg .
        //fetchWeatherById()
    }


}