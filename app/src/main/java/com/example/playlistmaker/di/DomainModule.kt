package com.example.playlistmaker.di

import com.example.playlistmaker.domain.player.interactors.MediaPlayerInteractor
import com.example.playlistmaker.domain.player.interactors.TrackListInteractor
import com.example.playlistmaker.domain.player.interactors_impl.MediaPlayerInteractorImpl
import com.example.playlistmaker.domain.player.interactors_impl.TrackListInteractorImpl
import com.example.playlistmaker.domain.search.Interactor.HistoryTrackListInteractor
import com.example.playlistmaker.domain.search.Interactor.SearchActivityStateInteractor
import com.example.playlistmaker.domain.search.Interactor.SearchTrackListInteractor
import com.example.playlistmaker.domain.search.InteractorImpl.HistoryTrackListInteractorImpl
import com.example.playlistmaker.domain.search.InteractorImpl.SearchActivityStateInteractorImpl
import com.example.playlistmaker.domain.search.InteractorImpl.SearchTrackListInteractorImpl
import com.example.playlistmaker.domain.settings.interactor.SettingsInteractor
import com.example.playlistmaker.domain.settings.interactor.SharingInteractor
import com.example.playlistmaker.domain.settings.interactor_impl.SettingsInteractorImpl
import com.example.playlistmaker.domain.settings.interactor_impl.SharingInteractorImpl
import org.koin.dsl.module

val domainModule = module {
    single<HistoryTrackListInteractor> { HistoryTrackListInteractorImpl(get()) }
    single<SearchActivityStateInteractor> { SearchActivityStateInteractorImpl(get()) }
    single<SearchTrackListInteractor> { SearchTrackListInteractorImpl(get()) }


    single<SharingInteractor> { SharingInteractorImpl(get()) }
    single<SettingsInteractor> { SettingsInteractorImpl(get()) }

    single<MediaPlayerInteractor>{ MediaPlayerInteractorImpl(get()) }
    single<TrackListInteractor> { TrackListInteractorImpl(get()) }

}