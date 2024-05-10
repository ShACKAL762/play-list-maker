package com.example.playlistmaker.ui.settings.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.Creator
import com.example.playlistmaker.data.settings.ExternalNavigator
import com.example.playlistmaker.domain.settings.interactor_impl.SharingInteractorImpl

@Suppress("UNCHECKED_CAST")
class SettingViewModelFactory (context:Context): ViewModelProvider.Factory {

    private val settingsInteractor by lazy {
        Creator.provideSettingInteractor(context)
    }
    private val sharingInteractor by lazy {
        Creator.provideSharingInteractor(context)
    }
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SettingViewModel(sharingInteractor = sharingInteractor,settingsInteractor = settingsInteractor) as T
    }
}