package com.example.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class App : Application() {
    private val prefName = "main_preferences"
    private var darkTheme = false
    override fun onCreate() {

        super.onCreate()
        darkTheme = getSharedPreferences(prefName, MODE_PRIVATE).getBoolean("darkTheme",false)
        switchTheme(darkTheme)
    }
    fun switchTheme(darkThemeEnabled: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                getSharedPreferences(prefName, MODE_PRIVATE).edit().putBoolean("darkTheme",true).apply()
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                getSharedPreferences(prefName, MODE_PRIVATE).edit().putBoolean("darkTheme",false).apply()
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}