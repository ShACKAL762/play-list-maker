package com.example.playlistmaker.domain.interactors_impl

import com.example.playlistmaker.domain.repositories.MediaPlayerRepository
import com.example.playlistmaker.domain.interactors.MediaPlayerInteractor
import com.example.playlistmaker.domain.model.PlayerState

class MediaPlayerInteractorImpl(private val mediaPlayerRepository: MediaPlayerRepository):
    MediaPlayerInteractor {
    override fun playerPrepare(previewUrl: String) {
        mediaPlayerRepository.playerPrepare(previewUrl)
    }

    override fun play() {
        mediaPlayerRepository.play()
    }

    override fun pause() {
        mediaPlayerRepository.pause()
    }

    override fun release() {
       mediaPlayerRepository.pause()
    }
    override fun playerState():PlayerState{
        return mediaPlayerRepository.playerState()
    }

    override fun playerControl() {
        mediaPlayerRepository.playerControl()
    }

    override fun currentMills(): Int {
        return mediaPlayerRepository.currentMills()
    }
}