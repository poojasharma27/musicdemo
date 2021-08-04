package com.musicdemo.ui

import android.app.ActivityManager
import android.content.*
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.musicdemo.*
import com.musicdemo.databinding.ActivityMainBinding
import com.musicdemo.services.MusicPlayerService
import com.musicdemo.ui.weather.WeatherActivity

class MusicActivity : AppCompatActivity(), View.OnClickListener {

    private var binding: ActivityMainBinding? = null
    val audioLink = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"

  //  private var musicPlaying: Boolean = false
    private lateinit var serviceIntent: Intent
    private lateinit var mService: MusicPlayerService
    private var mBound: Boolean = false

    var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            intent?.apply {
                when (this.getStringExtra(Music.MUSIC.name)) {
                    Music.PLAY.name -> {
                        mService.playMusic()
                    }
                    Music.PAUSE.name -> {
                        mService.pauseMusic()
                    }
                }
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding?.also {
            setContentView(it.root)
            it.playStopImageView.setBackgroundResource(R.drawable.ic_baseline_play_circle_outline_24)
            it.musicImageView.animate()?.translationY(0f)?.setDuration(2000)?.startDelay = 2900
            it.addImageView.setOnClickListener(this)
            it.musicSeekBar.isEnabled = false

            enablePlay(it)
        }
        serviceIntent = Intent(this, MusicPlayerService::class.java)

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(broadcastReceiver, IntentFilter(Music.MUSIC.name))

        addOnClick()

    }

    private fun addOnClick() {
        binding?.apply {
            play.setOnClickListener {
                enablePause(this)
                playAudio()

            }
            pause.setOnClickListener {
                enablePlay(this)
                mService.pauseMusic()
            }

            weatherButton.setOnClickListener {
                startActivity(Intent(this@MusicActivity, WeatherActivity::class.java))
            }
        }
    }

    fun enablePlay(binding: ActivityMainBinding?) {
        binding?.let {
            binding.pause.isEnabled = false
            binding.play.isEnabled = true
        }
    }

    fun enablePause(binding: ActivityMainBinding?) {
        binding?.let {
            binding.pause.isEnabled = true
            binding.play.isEnabled = false
        }
    }

    private fun addNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)
            Log.e("TAG", "true")
        } else {
            startService(serviceIntent)
            Log.e("TAG", "false")

        }
    }


    private fun playAudio() {
        if (!mService.isServiceStarted) {
            startService(serviceIntent)

        } else {
            mService.playMusic()

        }
        initialiseSeekBar()

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
        manager.getRunningServices(Integer.MAX_VALUE)) {
            if (java.name.equals(service.service.className)) {
                return true
            }
        }
        return false
    }


    /**
     * Service Connection for bound service
     */
    private val connection = object : ServiceConnection {
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
        //TODO add condition - launch notification only in case music is playing
        //addNotification()
        unbindService(connection)
        mBound = false
    }


    override fun onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)

        super.onDestroy()
    }

    /**
     * seekbar implementation and updating according to service behaviour
     */
   /* private fun initialiseSeekBar() {
            binding?.musicSeekBar?.max = mService.getDuration()%1000
            val handler = Handler(mainLooper)
            handler.post(object : Runnable {
                override fun run() {
                    try {
                        binding?.musicSeekBar?.progress = mService.getCurrentPosition()%1000
                        handler.postDelayed(this, 1000)
                    } catch (e: Exception) {
                        binding?.musicSeekBar?.progress = 0
                        Log.d("TAG", "Seekbar Error")
                    }

                }

            })
        binding?.musicSeekBar?.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (mService.mediaPlayer != null && fromUser) {
                    mService.mediaPlayer.seekTo(progress * 1000)
                }
            }
        })
    }
*/
    private fun initialiseSeekBar() {
        binding?.musicSeekBar?.max = mService.getDuration()
        val handler = Handler(mainLooper)
        handler.postDelayed(object : Runnable {
            override fun run() {
                try {
                    binding?.musicSeekBar?.progress = mService.getCurrentPosition()
                    handler.postDelayed(this, 1000)
                } catch (e: Exception) {
                    binding?.musicSeekBar?.progress = 0
                    Log.d("TAG", "Seekbar Error")
                }

            }

        }, 0)
    }
    override fun onClick(v: View?) {
        val intent = Intent(this, ContactActivity::class.java)
        startActivity(intent)
    }


}