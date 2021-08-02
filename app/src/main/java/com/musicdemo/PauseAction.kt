package com.musicdemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class PauseAction() : BroadcastReceiver() {
    override fun onReceive(context: Context?, p1: Intent?) {
        context?.apply {
            val localIntent =
                Intent(context, MusicActivity::class.java)
                    .putExtra(Music.MUSIC.name, Music.PAUSE.name)
                    .setAction(Music.MUSIC.name)

            LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent)
        }
    }
}