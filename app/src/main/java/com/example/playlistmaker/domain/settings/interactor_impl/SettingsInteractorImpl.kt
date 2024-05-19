package com.example.playlistmaker.domain.settings.interactor_impl


import com.example.playlistmaker.domain.settings.interactor.SettingsInteractor
import com.example.playlistmaker.domain.settings.repository.SettingsRepository

class SettingsInteractorImpl(private val settingRepository:SettingsRepository):SettingsInteractor {
    override fun getThemeSettings(): Boolean {
        return settingRepository.getThemeSettings()
    }

    override fun updateThemeSetting(checked: Boolean) {
        return settingRepository.updateThemeSetting(checked)
    }
}