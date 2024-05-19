package com.example.playlistmaker.domain.settings.interactor

interface SettingsInteractor {
    fun getThemeSettings(): Boolean
    fun updateThemeSetting(checked: Boolean)
}