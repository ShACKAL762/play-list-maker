package com.example.playlistmaker.domain.settings.repository



interface SettingsRepository {
    fun getThemeSettings(): Boolean
    fun updateThemeSetting(checked: Boolean)
}