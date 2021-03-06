package com.musicdemo.ui.weather

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.musicdemo.databinding.ActivityWeatherBinding
import com.musicdemo.listeners.OnRecyclerViewItemClickListener
import com.musicdemo.model.apientities.Weather
import com.musicdemo.network.ApiState
import com.musicdemo.ui.weather.details.WeatherDetailsActivity
import com.musicdemo.util.weatherItemClickedTrigger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WeatherActivity : AppCompatActivity(), OnRecyclerViewItemClickListener {

    private val TAG: String = "WeatherActivity"
    val viewModel: WeatherViewModel by viewModels()

    private var _binding: ActivityWeatherBinding? = null
    private val binding
        get() = _binding!!

    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityWeatherBinding.inflate(layoutInflater)
        binding.viewModel = viewModel

        handler = Handler(mainLooper)

        setContentView(binding.root)

        addEditTextWatcher()

        lifecycleScope.launch {
            weatherItemClickedTrigger.collect {
                if (it != -1) {
                    gotToDetail(viewModel.weatherObservableList[it])
                }
            }
        }


    }


    private fun gotToDetail(weather: Weather?) {
        val intent = Intent(this@WeatherActivity, WeatherDetailsActivity::class.java)
        intent.putExtra("data", weather)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()


    }

    private fun addEditTextWatcher() {
        binding.editText.addTextChangedListener {
            if (it.toString().isEmpty()) {
                return@addTextChangedListener
            }
            handler.removeCallbacksAndMessages(null)
            handler.postDelayed({
                viewModel.getWeatherByLocation()
            }, 500)

        }
    }

    lateinit var weatherJob: Job

    fun updateUi(state: ApiState) {
        when (state) {
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
        weatherJob = lifecycleScope.launch {
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

    override fun onItemClick(view: View, position: Int) {
        Log.d(TAG, "onItemClick:  ${viewModel.weatherObservableList[position]}")
    }


}