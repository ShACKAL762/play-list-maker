package com.example.playlistmaker.create

import android.content.Context
import com.example.playlistmaker.data.main.MainMenuReopsitoryImpl
import com.example.playlistmaker.domain.main.interactor.MainMenuInteractor
import com.example.playlistmaker.domain.main.iteractor_impl.MainMenuInteractorImpl
import com.example.playlistmaker.domain.main.repository.MainMenuRepository


object CreatorMainView {
    /***
    Create MainMenu
     */
    fun provideMainMenuInteract(context: Context): MainMenuInteractor {
        return MainMenuInteractorImpl(provideMainMenu(context))
    }

    private fun provideMainMenu(context: Context): MainMenuRepository {
        return MainMenuReopsitoryImpl(context)
    }
}