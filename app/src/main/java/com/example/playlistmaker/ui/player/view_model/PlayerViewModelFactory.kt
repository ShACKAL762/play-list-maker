package com.example.playlistmaker.ui.player.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.create.CreatorPlayerView


class PlayerViewModelFactory(context: Context): ViewModelProvider.Factory {

    private val playerInteractor by lazy {
        CreatorPlayerView.provideMediaPlayerInteract()
    }
    private val playerTrackInteractor by lazy {
        CreatorPlayerView.provideTrackListInteractor(context)
    }
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlayerViewModel(playerInteractor,playerTrackInteractor)as T
    }
}
