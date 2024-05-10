package com.example.playlistmaker.domain.settings.repository

import com.example.playlistmaker.data.settings.ThemeSettings

interface SettingsRepository {
    fun getThemeSettings(): ThemeSettings
    fun updateThemeSetting(settings: ThemeSettings)
}