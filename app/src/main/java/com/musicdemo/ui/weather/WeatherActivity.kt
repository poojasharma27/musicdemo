package com.musicdemo.ui.weather

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.musicdemo.databinding.ActivityWeatherBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherActivity : AppCompatActivity() {

    val viewModel : WeatherViewModel by viewModels()

     private var _binding: ActivityWeatherBinding? = null
     private val binding
         get() = _binding!!

    // lateinit var binding: ActivityWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWeatherBinding.inflate(layoutInflater)
        binding.viewModel = viewModel

    }

    override fun onStart() {
        super.onStart()

        viewModel.getWeatherByLocation()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}