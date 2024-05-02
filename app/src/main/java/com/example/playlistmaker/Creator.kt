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
    fun provideMediaPlayerInteractImp(): MediaPlayerInteractor {
        return MediaPlayerInteractorImpl(providePlayerManager())
    }
    fun provideTrackListInteractorImp(): TrackListInteractor {
        return TrackListInteractorImpl(provideTrackUseCaseImp())
    }
    private fun provideTrackUseCaseImp(): TrackListRepository {
        return TrackListRepoImpl()
    }

    private fun providePlayerManager(): MediaPlayerRepository {
        return MediaPlayerRepositoryImpl()
    }
}