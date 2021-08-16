package com.musicdemo.ui.weather.details

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.musicdemo.databinding.ActivityWeatherDetailsBinding

class WeatherDetailsActivity : AppCompatActivity() {

    val viewModel: WeatherDetailsViewModel by viewModels()

    private var _binding: ActivityWeatherDetailsBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityWeatherDetailsBinding.inflate(layoutInflater)
        binding.viewModel = viewModel

        setContentView(binding.root)


    }
}