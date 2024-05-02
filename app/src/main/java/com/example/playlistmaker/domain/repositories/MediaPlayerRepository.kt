package com.example.playlistmaker.domain.repositories


import com.example.playlistmaker.domain.model.PlayerState

interface MediaPlayerRepository {
    fun playerPrepare(previewUrl: String)
    fun play()
    fun pause()
    fun release()
    fun playerState(): PlayerState
    fun playerControl()
    fun currentMills():Int
}