package com.example.playlistmaker.create

import android.content.Context
import com.example.playlistmaker.data.player.MediaPlayerRepositoryImpl
import com.example.playlistmaker.data.TrackListRepositoryImpl
import com.example.playlistmaker.data.settings.ExternalNavigator
import com.example.playlistmaker.data.settings.SettingRepositoryImpl
import com.example.playlistmaker.domain.player.interactors.MediaPlayerInteractor
import com.example.playlistmaker.domain.player.interactors_impl.MediaPlayerInteractorImpl
import com.example.playlistmaker.domain.repositories.MediaPlayerRepository
import com.example.playlistmaker.domain.player.interactors.TrackListInteractor
import com.example.playlistmaker.domain.player.interactors_impl.TrackListInteractorImpl
import com.example.playlistmaker.domain.repositories.TrackListRepository
import com.example.playlistmaker.domain.settings.interactor.SettingsInteractor
import com.example.playlistmaker.domain.settings.interactor.SharingInteractor
import com.example.playlistmaker.domain.settings.interactor_impl.SettingsInteractorImpl
import com.example.playlistmaker.domain.settings.interactor_impl.SharingInteractorImpl
import com.example.playlistmaker.domain.settings.repository.SettingsRepository
import com.example.playlistmaker.domain.settings.repository.SharingRepoitory

object Creator {
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
        return TrackListRepositoryImpl(context)
    }

    /***
    Create Settings
     */
    fun provideSettingInteractor(context: Context): SettingsInteractor {
        return SettingsInteractorImpl(provideSettings(context))
    }

    private fun provideSettings(context: Context): SettingsRepository {
        return SettingRepositoryImpl(context)

    }


    /***
    Create Sharing
     */
    fun provideSharingInteractor(context: Context): SharingInteractor {
        return SharingInteractorImpl(provideSharing(context))
    }

    private fun provideSharing(context: Context): SharingRepoitory {
        return ExternalNavigator(context)
    }
    /***
    Create Main
     */


}