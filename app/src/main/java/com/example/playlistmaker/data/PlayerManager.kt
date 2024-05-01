package com.example.playlistmaker.data

import android.media.MediaPlayer
import com.example.playlistmaker.presentation.Player

class PlayerManager: Player {
    private lateinit var mediaPlayer : MediaPlayer
    override fun playerPrepare(previewUrl: String): MediaPlayer {

        mediaPlayer = MediaPlayer()
        mediaPlayer.setDataSource(previewUrl)
        mediaPlayer.prepareAsync()
        return mediaPlayer
    }



    override fun play() {
        mediaPlayer.start()
    }

    override fun pause() {
        mediaPlayer.pause()
    }

    override fun release() {
        mediaPlayer.release()
    }
}