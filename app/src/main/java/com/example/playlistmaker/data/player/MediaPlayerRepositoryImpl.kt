package com.example.playlistmaker.data.player

import android.media.MediaPlayer
import com.example.playlistmaker.domain.player.state.PlayerState
import com.example.playlistmaker.domain.player.repositories.MediaPlayerRepository



class MediaPlayerRepositoryImpl : MediaPlayerRepository {
    private lateinit var mediaPlayer: MediaPlayer
    private var playerState = PlayerState.DEFAULT
    override fun playerPrepare(previewUrl: String) {

        mediaPlayer = MediaPlayer()
        mediaPlayer.setDataSource(previewUrl)
        mediaPlayer.prepareAsync()

        mediaPlayer.setOnPreparedListener {
            playerState = PlayerState.PREPARED
        }
        mediaPlayer.setOnCompletionListener {
            playerState = PlayerState.PREPARED
        }
    }
    override fun play() {
        playerState = PlayerState.PLAY
        mediaPlayer.start()
    }
    override fun pause() {
        playerState = PlayerState.PAUSE
        mediaPlayer.pause()
    }
    override fun release() {
        playerState = PlayerState.RELEASE
        mediaPlayer.release()
    }
    override fun playerState(): PlayerState {
        return playerState
    }
    override fun playerControl() {
        if (mediaPlayer.isPlaying)
            pause()
        else
            play()
    }
    override fun currentMills(): Int {
        return mediaPlayer.currentPosition
    }

}