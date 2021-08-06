package com.musicdemo.ui.weather

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.musicdemo.R
import com.musicdemo.databinding.ActivityWeatherBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WeatherActivity : AppCompatActivity() {

    private val TAG: String? ="WeatherActivity"
    val viewModel : WeatherViewModel by viewModels()

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
        },2000)

        addEditTextWatcher()
    }

    override fun onResume() {
        super.onResume()

        viewModel.getWeatherByLocation()

    }

    private fun addEditTextWatcher() {
        binding.editText.addTextChangedListener {
            Log.d(TAG, "addEditTextWatcher: ${viewModel.location.get()} ")
        }
    }

    override fun onStart() {
        super.onStart()

    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null

    }


}