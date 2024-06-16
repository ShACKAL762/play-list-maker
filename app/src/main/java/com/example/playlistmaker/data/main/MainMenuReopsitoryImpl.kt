package com.example.playlistmaker.data.main

import android.content.Context
import android.content.Intent
import com.example.playlistmaker.domain.main.repository.MainMenuRepository
import com.example.playlistmaker.ui.library.activity.LibraryFragment
import com.example.playlistmaker.ui.search.activity.SearchFragment
import com.example.playlistmaker.ui.settings.activity.SettingFragment

class MainMenuReopsitoryImpl(private val context: Context): MainMenuRepository {
    override fun startSearch() {
        context.startActivity(Intent(context, SearchFragment::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    override fun startLibrary() {
        context.startActivity(Intent(context, LibraryFragment::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    override fun startSettings() {
        context.startActivity(Intent(context, SettingFragment::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }
}