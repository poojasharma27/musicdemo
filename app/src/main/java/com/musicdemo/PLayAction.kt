package com.musicdemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class PLayAction : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        MusicPlayerService.mediaPlayer.start()
    }
}