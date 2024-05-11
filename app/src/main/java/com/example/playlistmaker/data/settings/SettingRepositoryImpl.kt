package com.example.playlistmaker.data.settings

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.example.playlistmaker.App
import com.example.playlistmaker.domain.settings.repository.SettingsRepository
class SettingRepositoryImpl (private val context: Context): SettingsRepository {
    private val prefName = "main_preferences"
    override fun getThemeSettings(): Boolean {
        return context.getSharedPreferences(prefName, MODE_PRIVATE).getBoolean("darkTheme", false)
    }
    override fun updateThemeSetting(checked: Boolean) {
        (context as App).switchTheme(checked)
    }


}