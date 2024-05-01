package com.example.playlistmaker.domain

import com.example.playlistmaker.data.MediaPlayerRepositoryImp
import com.example.playlistmaker.domain.model.PlayerState

class MediaPlayerInteractorImp(private val mediaPlayer: MediaPlayerRepositoryImp): MediaPlayerInteractor {
    override fun playerPrepare(previewUrl: String) {
        mediaPlayer.playerPrepare(previewUrl)
    }

    override fun play() {
        mediaPlayer.play()
    }

    override fun pause() {
        mediaPlayer.pause()
    }

    override fun release() {
       mediaPlayer.pause()
    }
    override fun playerState():PlayerState{
        return mediaPlayer.playerState()
    }

    override fun playerControl() {
        mediaPlayer.playerControl()
    }

    override fun currentMills(): Int {
        return mediaPlayer.currentMills()
    }
}