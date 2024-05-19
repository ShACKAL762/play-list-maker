package com.example.playlistmaker.domain.player.repositories


import com.example.playlistmaker.data.player.state.PlayerState

interface MediaPlayerRepository {
    fun playerPrepare(previewUrl: String)
    fun play()
    fun pause()
    fun release()
    fun playerState(): PlayerState
    fun playerControl()
    fun currentMills():Int
}