package com.example.playlistmaker.create

import android.content.Context
import com.example.playlistmaker.data.TrackListRepositoryImpl
import com.example.playlistmaker.data.main.MainMenuReopsitoryImpl
import com.example.playlistmaker.data.player.MediaPlayerRepositoryImpl
import com.example.playlistmaker.domain.main.interactor.MainMenuInteractor
import com.example.playlistmaker.domain.main.iteractor_impl.MainMenuInteractorImpl
import com.example.playlistmaker.domain.main.repository.MainMenuRepository
import com.example.playlistmaker.domain.player.interactors.MediaPlayerInteractor
import com.example.playlistmaker.domain.player.interactors.TrackListInteractor
import com.example.playlistmaker.domain.player.interactors_impl.MediaPlayerInteractorImpl
import com.example.playlistmaker.domain.player.interactors_impl.TrackListInteractorImpl
import com.example.playlistmaker.domain.repositories.MediaPlayerRepository
import com.example.playlistmaker.domain.repositories.TrackListRepository

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