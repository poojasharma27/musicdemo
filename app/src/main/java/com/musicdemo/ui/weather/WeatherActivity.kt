package com.musicdemo.ui.weather

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.musicdemo.databinding.ActivityWeatherBinding
import com.musicdemo.network.ApiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WeatherActivity : AppCompatActivity() {

    private val TAG: String? = "WeatherActivity"
    val viewModel: WeatherViewModel by viewModels()

    private var _binding: ActivityWeatherBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityWeatherBinding.inflate(layoutInflater)
        binding.viewModel = viewModel

        setContentView(binding.root)

        Handler(mainLooper).postDelayed({
            viewModel.updateLocation()
        }, 2000)

        addEditTextWatcher()
    }

    override fun onResume() {
        super.onResume()

        //get weather data
        viewModel.getWeatherByLocation()

    }

    private fun addEditTextWatcher() {
        binding.editText.addTextChangedListener {
            viewModel.getWeatherByLocation()
        }
    }

    lateinit var weatherJob : Job

    fun updateUi(state: ApiState){
        when(state){
            ApiState.Empty -> {

            }
            is ApiState.Failure -> {

            }
            ApiState.Loading -> {

            }
            is ApiState.Success<*> -> {

            }
        }
    }

    override fun onStart() {
        super.onStart()
        startWeatherJob()
    }

    private fun startWeatherJob() {
        weatherJob =  lifecycleScope.launch {
            viewModel.apiState.collect {
                updateUi(it)
            }
        }

        weatherJob.start()
    }

    override fun onStop() {
        super.onStop()
        weatherJob.cancel()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null

    }


}