package com.musicdemo

import android.app.Service
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder
import android.widget.Toast
import java.io.InputStream


class MusicPlayerService : Service(), MediaPlayer.OnCompletionListener,
    MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
    MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnInfoListener,
    MediaPlayer.OnBufferingUpdateListener {

    private lateinit var mediaPlayer: MediaPlayer
    var link :String?= null

    override fun onCreate() {
        super.onCreate()

       //val file  = resources.openRawResourceFd(R.raw.sound)
        val TAG  = "MusicPlayerService"

       // mediaPlayer=MediaPlayer.create(this, Uri.parse("https://vodafoneapp.s3.ap-south-1.amazonaws.com/gurushala/pages/0.6249899713356712Q2%20P6-%20Art%20Integrated%20Learning.mp3"));
        mediaPlayer = MediaPlayer()
        mediaPlayer.setOnCompletionListener(this)
        mediaPlayer.setOnPreparedListener(this)
        mediaPlayer.setOnErrorListener(this)
        mediaPlayer.setOnSeekCompleteListener(this)
        mediaPlayer.setOnInfoListener(this)
        mediaPlayer.setOnBufferingUpdateListener(this)
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        link= intent?.getStringExtra("AudioLink")
        //mediaPlayer.reset()
        if (!mediaPlayer.isPlaying){
            try {
              mediaPlayer.setDataSource("https://vodafoneapp.s3.ap-south-1.amazonaws.com/gurushala/pages/0.6249899713356712Q2%20P6-%20Art%20Integrated%20Learning.mp3")
                  //mediaPlayer.setDataSource("https://vodafoneapp.s3.ap-south-1.amazonaws.com/gurushala/pages/0.6249899713356712Q2%20P6-%20Art%20Integrated%20Learning.mp3")
                mediaPlayer.prepare()
                mediaPlayer.start()
            }catch (e:Exception){
                Toast.makeText(this," $TAG  : ${e.localizedMessage}",Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer != null){
            if (mediaPlayer.isPlaying){
                mediaPlayer.stop()
            }
            mediaPlayer.release()
        }
    }

    override fun onCompletion(mp: MediaPlayer?) {
        if (mp?.isPlaying == true){
            mp.stop()

        }
        stopSelf()
    }

    override fun onPrepared(mp: MediaPlayer?) {
        if (mp?.isPlaying == true){
            mp.start()

        }
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        when(what){
       MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK->{
           Toast.makeText(this,"MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK",Toast.LENGTH_SHORT).show()

       }
            MediaPlayer.MEDIA_ERROR_SERVER_DIED->{
                Toast.makeText(this,"MEDIA_ERROR_SERVER_DIED",Toast.LENGTH_SHORT).show()

            }
            MediaPlayer.MEDIA_ERROR_UNKNOWN->{
                Toast.makeText(this,"MEDIA_ERROR_UNKNOWN",Toast.LENGTH_SHORT).show()

            }
        }
        return false
    }

    override fun onSeekComplete(mp: MediaPlayer?) {
    }

    override fun onInfo(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        return false
    }

    override fun onBufferingUpdate(mp: MediaPlayer?, percent: Int) {
    }
}