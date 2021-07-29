package com.musicdemo

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.util.Log

class PauseAction() : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        Log.d("BROADCAST", "in broadcast")
        MusicPlayerService.mediaPlayer.pause()
        //MyForegroundService().
    }
}