package com.example.playlistmaker.domain.settings.repository



interface SettingsRepository {
    fun isChecked(): Boolean
    fun updateThemeSetting(isChecked: Boolean)
}