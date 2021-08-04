package com.musicdemo.listeners

interface MusicPlayerListener {
    fun pauseMusic()
    fun getDuration(): Int
    fun getCurrentPosition(): Int
    fun playMusic()

}