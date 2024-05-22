package com.example.playlistmaker.ui.main.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.create.CreatorMainView
import com.example.playlistmaker.ui.settings.view_model.SettingViewModel

class MainActivityViewModelFactory (context: Context): ViewModelProvider.Factory {
    private val mainMenuInteractor by lazy {
        CreatorMainView.provideMainMenuInteract(context)
    }
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainActivityViewModel(mainMenuInteractor) as T
    }
}