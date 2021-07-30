package com.musicdemo

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.musicdemo.databinding.ActivityMainBinding

class MusicActivity : AppCompatActivity(), MusicStoppedListener,View.OnClickListener {

    private var binding: ActivityMainBinding? = null
    val audioLink = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
     private  var musicPlaying: Boolean = false
     private  lateinit var serviceIntent: Intent
    private lateinit var mService: MusicPlayerService
    private var mBound: Boolean = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.playStopImageView?.setBackgroundResource(R.drawable.ic_baseline_play_circle_outline_24)
        serviceIntent = Intent(this, MusicPlayerService::class.java)
        MyApplication.mContext = this
        binding?.musicImageView?.animate()?.translationY(0f)?.setDuration(2000)?.setStartDelay(2900)
        binding?.addImageView?.setOnClickListener(this)
        binding?.musicSeekBar?.isEnabled = false
        binding?.pause?.isEnabled = false

        binding?.play?.setOnClickListener {
            if (!musicPlaying) {
                binding?.pause?.isEnabled = true
                binding?.play?.isEnabled = false
                playAudio()
                initialiseSeekBar()
                musicPlaying = true

            }
        }
        binding?.pause?.setOnClickListener {
       binding?.pause?.isEnabled = false
       binding?.play?.isEnabled = true
       stopPlayService()
       musicPlaying = false
       }

    }

    override fun onPause() {
        super.onPause()
        addNotification()
    }

    private fun addNotification() {
        if (musicPlaying.equals(true)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(serviceIntent)
                Log.e("TAG", "true")
            } else {
                startService(serviceIntent)
                Log.e("TAG", "false")

            }
        }
    }


    private fun stopPlayService() {
        if (isMyServiceRunning(MusicPlayerService::class.java)){
            MusicPlayerService.mediaPlayer.pause()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                stopService(intent)
            }
        }
    }

    private fun playAudio() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startService(serviceIntent)
                MusicPlayerService.mediaPlayer.start()
            }
        } catch (e: SecurityException) {
            Toast.makeText(this, "Error" + e.message, Toast.LENGTH_SHORT).show()
        }

    }
    /**
     * bind service on start to avoid late init media player is not initialised
     */
        override fun onStart() {
        super.onStart()
        bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE)

        }


   /**
    * checking that is my service is running or not
    */

   private fun isMyServiceRunning(java: Class<MusicPlayerService>): Boolean {
        val manager: ActivityManager = getSystemService(
            ACTIVITY_SERVICE
        ) as ActivityManager

        for (service: ActivityManager.RunningServiceInfo in
        manager.getRunningServices(Integer.MAX_VALUE)){
            if (java.name.equals(service.service.className)){
                return true
            }
        }
        return false
    }



    /**
     * Service Connection for bound service
     */
    private val connection  = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName?, service: IBinder?) {
            val binder = service as MusicPlayerService.LocalBinder
            mService = binder.getService()
            mBound = true
        }
        override fun onServiceDisconnected(p0: ComponentName?) {
            mBound = false
        }
    }


    /**
     * onStop and free the connect
     */
    override fun onStop() {
        super.onStop()
        unbindService(connection)
        mBound = false
    }



    /**
     * musicStop then automatic complete
     */
      override fun onMusicStop() {
        binding?.play?.isEnabled=true
        binding?.pause?.isEnabled=false
        musicPlaying = false
    }



    /**
     * seekbar implementation and updating according to service behaviour
     */
         private fun initialiseSeekBar() {
        binding?.musicSeekBar?.max = MusicPlayerService.mediaPlayer.duration
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                try {
                    binding?.musicSeekBar?.progress = MusicPlayerService.mediaPlayer.currentPosition
                    handler.postDelayed(this, 0)
                } catch (e: Exception) {
                    binding?.musicSeekBar?.progress = 0
                    Log.d("TAG", "Seekbar Error")
                }

            }

        }, 0)
    }

    override fun onClick(v: View?) {
        val intent = Intent(this,ContactActivity::class.java)
        startActivity(intent)
    }


}