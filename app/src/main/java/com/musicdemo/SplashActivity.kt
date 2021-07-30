package com.musicdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.musicdemo.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

 private var binding: ActivitySplashBinding?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.titleTextView?.animate()?.translationY(-1400f)?.setDuration(2700)?.setStartDelay(0)
        binding?.animationView?.animate()?.translationY(1000f)?.setDuration(2000)?.setStartDelay(2900)

        onClickView()

    }

    private fun onClickView() {
        val handler = Handler()
        handler.postDelayed({ startActivity(Intent(this@SplashActivity,MusicActivity::class.java)) }, 8000)
    }
}


