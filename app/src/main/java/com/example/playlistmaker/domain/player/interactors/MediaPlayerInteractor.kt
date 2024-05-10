package com.example.playlistmaker.domain.player.interactors


import com.example.playlistmaker.domain.model.PlayerState

interface MediaPlayerInteractor {
    fun playerPrepare(previewUrl: String)
    fun play()
    fun pause()
    fun release()
    fun playerState(): PlayerState
    fun playerControl()
    fun currentMills():Int
}