package com.example.playlistmaker.ui.settings.view_model

import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.R
import com.example.playlistmaker.data.settings.ThemeSettings
import com.example.playlistmaker.domain.settings.interactor.SettingsInteractor
import com.example.playlistmaker.domain.settings.interactor.SharingInteractor

class SettingViewModel(
    private val sharingInteractor: SharingInteractor,
    private val settingsInteractor: SettingsInteractor,
) : ViewModel() {
    init {
        Log.e("ViewModelCreater","")
    }

    fun share (){
        Log.e("AAA","Share")
        sharingInteractor.shareApp()
    }
    fun openLink(){
        sharingInteractor.openTerms()
    }
    fun sendSupport(){
        sharingInteractor.openSupport()
    }
    fun changeTheme(){
        settingsInteractor.updateThemeSetting(ThemeSettings())
    }
    fun getTheme(){
        settingsInteractor.getThemeSettings()
    }
}