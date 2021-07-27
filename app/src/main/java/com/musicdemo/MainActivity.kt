package com.musicdemo

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.musicdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  private var binding:ActivityMainBinding?=null
    val audioLink="https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
    var musicPlaying :Boolean= false
    var mediaPlayer:MediaPlayer?=null
  lateinit  var serviceIntent : Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view= binding?.root
        setContentView(view)
        binding?.playStopImageView?.setBackgroundResource(R.drawable.ic_baseline_play_circle_outline_24)
        serviceIntent = Intent(this,MusicPlayerService::class.java)
      //  val file  = resources.openRawResourceFd(android.R.raw.sound)

        binding?.playStopImageView?.setOnClickListener {
          if (!musicPlaying){
              playAudio()
              binding?.playStopImageView?.setBackgroundResource(R.drawable.ic_baseline_pause_circle_outline_24)
              musicPlaying=true
          }
            else{
                stopPlayService()
              binding?.playStopImageView?.setBackgroundResource(R.drawable.ic_baseline_play_circle_outline_24)
              musicPlaying=false
          }
        }
    }

    private fun stopPlayService() {
        //mediaPlayer?.stop()
     try {
       stopService(serviceIntent)
     }catch (e:SecurityException){
        Toast.makeText(this,"Error"+e.message,Toast.LENGTH_SHORT).show()
       }
    }

    private fun playAudio() {
        /*mediaPlayer= MediaPlayer.create(this, Uri.parse("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"))
        mediaPlayer?.start()*/
      try {
             startService(serviceIntent)
         }catch (e:SecurityException){
             Toast.makeText(this,"Error"+e.message,Toast.LENGTH_SHORT).show()
         }

    }
}