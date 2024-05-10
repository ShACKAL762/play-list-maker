package com.example.playlistmaker.domain.settings.interactor

import com.example.playlistmaker.data.settings.ThemeSettings

interface SettingsInteractor {
    fun getThemeSettings(): ThemeSettings
    fun updateThemeSetting(settings: ThemeSettings)
}