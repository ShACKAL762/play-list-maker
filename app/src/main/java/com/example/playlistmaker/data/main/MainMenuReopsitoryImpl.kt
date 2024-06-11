package com.example.playlistmaker.data.main

import android.content.Context
import android.content.Intent
import com.example.playlistmaker.domain.main.repository.MainMenuRepository
import com.example.playlistmaker.ui.library.activity.LibraryActivity
import com.example.playlistmaker.ui.search.activity.SearchActivity
import com.example.playlistmaker.ui.settings.activity.SettingsActivity

class MainMenuReopsitoryImpl(private val context: Context): MainMenuRepository {
    override fun startSearch() {
        context.startActivity(Intent(context, SearchActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    override fun startLibrary() {
        context.startActivity(Intent(context, LibraryActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    override fun startSettings() {
        context.startActivity(Intent(context, SettingsActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }
}