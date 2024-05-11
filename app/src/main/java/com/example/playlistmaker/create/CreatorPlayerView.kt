package com.example.playlistmaker.create

import com.example.playlistmaker.data.TrackListRepositoryImpl
import com.example.playlistmaker.data.player.MediaPlayerRepositoryImpl
import com.example.playlistmaker.domain.player.interactors.MediaPlayerInteractor
import com.example.playlistmaker.domain.player.interactors.TrackListInteractor
import com.example.playlistmaker.domain.player.interactors_impl.MediaPlayerInteractorImpl
import com.example.playlistmaker.domain.player.interactors_impl.TrackListInteractorImpl
import com.example.playlistmaker.domain.repositories.MediaPlayerRepository
import com.example.playlistmaker.domain.repositories.TrackListRepository

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
    fun provideTrackListInteractor(): TrackListInteractor {
        return TrackListInteractorImpl(provideTrackGet())
    }

    private fun provideTrackGet(): TrackListRepository {
        return TrackListRepositoryImpl()
    }
}