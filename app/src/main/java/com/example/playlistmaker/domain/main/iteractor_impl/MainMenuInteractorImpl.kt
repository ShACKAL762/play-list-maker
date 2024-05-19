package com.example.playlistmaker.domain.main.iteractor_impl

import com.example.playlistmaker.domain.main.interactor.MainMenuInteractor
import com.example.playlistmaker.domain.main.repository.MainMenuRepository

class MainMenuInteractorImpl(private val mainMenu: MainMenuRepository): MainMenuInteractor {
    override fun startSearch() {
        mainMenu.startSearch()
    }

    override fun startLibrary() {
        mainMenu.startLibrary()
    }

    override fun startSettings() {
        mainMenu.startSettings()
    }
}