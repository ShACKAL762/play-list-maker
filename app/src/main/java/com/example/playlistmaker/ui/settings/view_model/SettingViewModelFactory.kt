package com.example.playlistmaker.ui.settings.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.create.CreatorSettingView

@Suppress("UNCHECKED_CAST")
class SettingViewModelFactory (context:Context): ViewModelProvider.Factory {

    private val settingsInteractor by lazy {
        CreatorSettingView.provideSettingInteractor(context.applicationContext)
    }
    private val sharingInteractor by lazy {
        CreatorSettingView.provideSharingInteractor(context)
    }
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SettingViewModel(sharingInteractor = sharingInteractor,settingsInteractor = settingsInteractor) as T
    }
}