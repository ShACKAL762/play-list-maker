package com.example.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.di.domainModule
import com.example.playlistmaker.di.repoModule
import com.example.playlistmaker.di.retrofitModule
import com.example.playlistmaker.di.viewModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    private val prefName = "main_preferences"
    private var darkTheme = false
    override fun onCreate() {
        startKoin {
            androidContext(this@App)
            modules(retrofitModule, viewModules, domainModule, repoModule)
        }

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