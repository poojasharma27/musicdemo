package com.musicdemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class PlayAction : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.apply {
            val localIntent =
                Intent(context, MusicActivity::class.java)
                    .putExtra(Music.MUSIC.name, Music.PLAY.name)
                    .setAction(Music.MUSIC.name)

            LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent)
        }
    }
}
