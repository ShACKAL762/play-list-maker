package com.example.playlistmaker.domain.settings.interactor_impl


import com.example.playlistmaker.data.settings.ThemeSettings
import com.example.playlistmaker.domain.settings.interactor.SettingsInteractor
import com.example.playlistmaker.domain.settings.repository.SettingsRepository

class SettingsInteractorImpl(private val settingRepository:SettingsRepository):SettingsInteractor {
    override fun getThemeSettings(): ThemeSettings {
        return settingRepository.getThemeSettings()
    }

    override fun updateThemeSetting(settings: ThemeSettings) {
        return settingRepository.updateThemeSetting(settings)
    }
}