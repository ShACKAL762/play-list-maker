package com.example.playlistmaker.ui.player.view_model.playerState

import com.example.playlistmaker.domain.entity.Track

sealed interface PlayerState {

    object Loading : PlayerState
    data class Prepared(
        val track: Track
    ) : PlayerState

    object Play:PlayerState


    object Pause : PlayerState
}
