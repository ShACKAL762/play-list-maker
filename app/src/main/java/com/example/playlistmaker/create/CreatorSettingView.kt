package com.example.playlistmaker.create

import android.content.Context
import com.example.playlistmaker.data.settings.ExternalNavigator
import com.example.playlistmaker.data.settings.SettingRepositoryImpl
import com.example.playlistmaker.domain.settings.interactor.SettingsInteractor
import com.example.playlistmaker.domain.settings.interactor.SharingInteractor
import com.example.playlistmaker.domain.settings.interactor_impl.SettingsInteractorImpl
import com.example.playlistmaker.domain.settings.interactor_impl.SharingInteractorImpl
import com.example.playlistmaker.domain.settings.repository.SettingsRepository
import com.example.playlistmaker.domain.settings.repository.SharingRepoitory

object CreatorSettingView {
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
}