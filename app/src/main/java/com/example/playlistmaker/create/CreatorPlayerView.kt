package com.example.playlistmaker.create

import android.content.Context
import com.example.playlistmaker.data.history.HistoryRepository
import com.example.playlistmaker.data.player.MediaPlayerRepositoryImpl
import com.example.playlistmaker.domain.player.interactors.MediaPlayerInteractor
import com.example.playlistmaker.domain.player.interactors.TrackListInteractor
import com.example.playlistmaker.domain.player.interactors_impl.MediaPlayerInteractorImpl
import com.example.playlistmaker.domain.player.interactors_impl.TrackListInteractorImpl
import com.example.playlistmaker.domain.player.repositories.MediaPlayerRepository
import com.example.playlistmaker.domain.player.repositories.TrackListRepository

object CreatorPlayerView {
    /***
    Create Player
     */
    fun provideMediaPlayerInteract(): MediaPlayerInteractor {
        return MediaPlayerInteractorImpl(providePlayerManager())
    }

    private fun providePlayerManager(): MediaPlayerRepository {
        return MediaPlayerRepositoryImpl()
    }

    /***
    Create TrackList
     */
    fun provideTrackListInteractor(context: Context): TrackListInteractor {
        return TrackListInteractorImpl(provideTrackGet(context))
    }

    private fun provideTrackGet(context: Context): TrackListRepository {
        return HistoryRepository(context)
    }
}