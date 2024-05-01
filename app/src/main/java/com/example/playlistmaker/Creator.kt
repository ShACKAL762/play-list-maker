package com.example.playlistmaker

import com.example.playlistmaker.data.MediaPlayerRepositoryImp
import com.example.playlistmaker.data.TrackUseCaseRepoImp
import com.example.playlistmaker.domain.MediaPlayerInteractorImp
import com.example.playlistmaker.domain.TrackListInteractorImp

object Creator {
    fun provideMediaPlayerInteractImp(): MediaPlayerInteractorImp {
        return MediaPlayerInteractorImp(providePlayerManager())
    }
    fun provideTrackListInteractorImp(): TrackListInteractorImp {
        return TrackListInteractorImp(provideTrackUseCaseImp())
    }
    private fun provideTrackUseCaseImp(): TrackUseCaseRepoImp {
        return TrackUseCaseRepoImp()
    }

    private fun providePlayerManager(): MediaPlayerRepositoryImp {
        return MediaPlayerRepositoryImp()
    }
}