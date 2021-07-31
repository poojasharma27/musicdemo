package com.musicdemo

interface MusicPlayerCallBacks {
    fun pauseMusic()
    fun getDuration(): Int
    fun getCurrentPosition(): Int

}