package com.example.playlistmaker.presentation

import android.media.MediaPlayer

interface Player {
    fun playerPrepare(previewUrl: String): MediaPlayer
    fun play()
    fun pause()
    fun release()
}