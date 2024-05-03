package com.example.playlistmaker

import com.example.playlistmaker.data.MediaPlayerRepositoryImpl
import com.example.playlistmaker.data.TrackListRepoImpl
import com.example.playlistmaker.domain.interactors.MediaPlayerInteractor
import com.example.playlistmaker.domain.impl.MediaPlayerInteractorImpl
import com.example.playlistmaker.domain.repositories.MediaPlayerRepository
import com.example.playlistmaker.domain.interactors.TrackListInteractor
import com.example.playlistmaker.domain.impl.TrackListInteractorImpl
import com.example.playlistmaker.domain.repositories.TrackListRepository

object Creator {
    fun provideMediaPlayerInteract(): MediaPlayerInteractor {
        return MediaPlayerInteractorImpl(providePlayerManager())
    }
    fun provideTrackListInteractor(): TrackListInteractor {
        return TrackListInteractorImpl(provideTrackGet())
    }
    private fun provideTrackGet(): TrackListRepository {
        return TrackListRepoImpl()
    }

    private fun providePlayerManager(): MediaPlayerRepository {
        return MediaPlayerRepositoryImpl()
    }
}