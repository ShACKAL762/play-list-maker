package com.example.playlistmaker.di

import com.example.playlistmaker.domain.library.interactors.AlbumListInteractor
import com.example.playlistmaker.domain.library.interactors.FavoriteListInteractor
import com.example.playlistmaker.domain.library.interactors_Impl.AlbumListInteractorImpl
import com.example.playlistmaker.domain.library.interactors_Impl.FavoriteListInteractorImpl
import com.example.playlistmaker.domain.player.interactors.MediaPlayerInteractor
import com.example.playlistmaker.domain.player.interactors.TrackListInteractor
import com.example.playlistmaker.domain.player.interactors_impl.MediaPlayerInteractorImpl
import com.example.playlistmaker.domain.player.interactors_impl.TrackListInteractorImpl
import com.example.playlistmaker.domain.search.interactor.HistoryTrackListInteractor
import com.example.playlistmaker.domain.search.interactor.SearchActivityStateInteractor
import com.example.playlistmaker.domain.search.interactor.SearchTrackListInteractor
import com.example.playlistmaker.domain.search.interactorImpl.HistoryTrackListInteractorImpl
import com.example.playlistmaker.domain.search.interactorImpl.SearchActivityStateInteractorImpl
import com.example.playlistmaker.domain.search.interactorImpl.SearchTrackListInteractorImpl
import com.example.playlistmaker.domain.settings.interactor.SettingsInteractor
import com.example.playlistmaker.domain.settings.interactor.SharingInteractor
import com.example.playlistmaker.domain.settings.interactor_impl.SettingsInteractorImpl
import com.example.playlistmaker.domain.settings.interactor_impl.SharingInteractorImpl
import org.koin.dsl.module

val domainModule = module {
    factory<HistoryTrackListInteractor> { HistoryTrackListInteractorImpl(get()) }
    factory<SearchActivityStateInteractor> { SearchActivityStateInteractorImpl(get()) }
    factory<SearchTrackListInteractor> { SearchTrackListInteractorImpl(get()) }

    factory<SharingInteractor> { SharingInteractorImpl(get()) }
    factory<SettingsInteractor> { SettingsInteractorImpl(get()) }

    factory<MediaPlayerInteractor>{ MediaPlayerInteractorImpl(get()) }
    factory<TrackListInteractor> { TrackListInteractorImpl(get()) }

    factory<FavoriteListInteractor> { FavoriteListInteractorImpl(get()) }

    factory<AlbumListInteractor>{AlbumListInteractorImpl(get())}
}