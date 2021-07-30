package com.musicdemo

import android.app.*
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


class MusicPlayerService : Service(), MediaPlayer.OnCompletionListener,
    MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {

    companion object {
         lateinit var mediaPlayer: MediaPlayer
    }

    private var link: String? = null
   private lateinit var musicStoppedListener: MusicStoppedListener
    private val notifyId = 1
  private  lateinit var manager: NotificationManager
    private var isMusic: Boolean = false

    private val binder = LocalBinder()

    inner class LocalBinder : Binder(){
        fun getService() : MusicPlayerService = this@MusicPlayerService
    }

    override fun onBind(p0: Intent?): IBinder {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        initMusic()
        createNotificationChannel()

    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        link = intent?.getStringExtra("AudioLink")
        if (!isMusic) {
            mediaPlayer.reset()
            musicStoppedListener = MyApplication.mContext as MusicStoppedListener
            if (!mediaPlayer.isPlaying) {
                try {
                    mediaPlayer.setDataSource("https://file-examples-com.github.io/uploads/2017/11/file_example_MP3_700KB.mp3")
                    mediaPlayer.prepare()
                    mediaPlayer.start()
                    isMusic = true
                } catch (e: Exception) {
                    Toast.makeText(this, " $TAG  : ${e.localizedMessage}", Toast.LENGTH_SHORT)
                        .show()
                    e.printStackTrace()
                }
            }
        } else {
            showNotification()
        }

        return START_STICKY
    }

    /*
    *createNotificationChannel
     */
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                "CHANNEL_ID",
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }

    }


    /**
     * initialising music and accessing raw
     */
    private fun initMusic() {
        mediaPlayer = MediaPlayer()
        mediaPlayer.setOnCompletionListener(this)
        mediaPlayer.setOnPreparedListener(this)
        mediaPlayer.setOnErrorListener(this)

    }
    /*
    showNotification
     */
    private fun showNotification() {
        val images: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.music)
        val notificationIntent = Intent(this, MusicActivity::class.java)
        val pausePendingIntent = PendingIntent.getBroadcast(this, 1, Intent(this, PauseAction::class.java).setAction("notification_paused"), PendingIntent.FLAG_UPDATE_CURRENT)
        val playPendingIntent = PendingIntent.getBroadcast(this, 2, Intent(this, PLayAction::class.java).setAction("notification_paused"), PendingIntent.FLAG_UPDATE_CURRENT)
        val notification = NotificationCompat.Builder(this, "CHANNEL_ID")
            .setContentText("Hello Tune")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setLargeIcon(images)
            .setSilent(true)
            .addAction(R.drawable.ic_baseline_pause_circle_outline_24 , "PAUSE" , pausePendingIntent)
            .addAction(R.drawable.ic_baseline_play_circle_outline_24, "PLAY", playPendingIntent)
        val max = mediaPlayer.duration
        var progress = 0
        val handler = Handler()

        /*
        Updating notification seekbar according to music progress
         */
        with(NotificationManagerCompat.from(this)){
            notification.setProgress(max, progress, false)
            startForeground(notifyId, notification.build())
            Thread(Runnable {
                while (progress < max){
                    progress = mediaPlayer.currentPosition
                    try {
                        Thread.sleep(1000)
                    } catch (e:InterruptedException){
                        e.printStackTrace()
                    }
                    handler.post(Runnable {
                        if (progress == max){
                            notification.setContentText("Finished")
                            notification.setProgress(0, 0, false)
                        } else {
                            notification.setProgress(max, mediaPlayer.currentPosition, false)
                        }
                        notify(notifyId, notification.build())
                    })
                }
            }).start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
            }
            mediaPlayer.release()
        }
    }

    override fun onCompletion(mp: MediaPlayer?) {
        if (mp?.isPlaying == true) {
            mp.stop()

        }
        musicStoppedListener.onMusicStop()
        stopSelf()
    }

    override fun onPrepared(mp: MediaPlayer?) {
        if (mp?.isPlaying == true) {
            mp.start()
        }
    }


    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        when (what) {
            MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK -> {
                Toast.makeText(
                    this,
                    "MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK",
                    Toast.LENGTH_SHORT
                ).show()

            }
            MediaPlayer.MEDIA_ERROR_SERVER_DIED -> {
                Toast.makeText(this, "MEDIA_ERROR_SERVER_DIED", Toast.LENGTH_SHORT).show()

            }
            MediaPlayer.MEDIA_ERROR_UNKNOWN -> {
                Toast.makeText(this, "MEDIA_ERROR_UNKNOWN", Toast.LENGTH_SHORT).show()

            }
        }
        return false
    }

}

