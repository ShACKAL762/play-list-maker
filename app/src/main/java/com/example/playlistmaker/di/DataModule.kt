package com.example.playlistmaker.di

import androidx.room.Room
import com.example.playlistmaker.data.converters.AlbumDbConverter
import com.example.playlistmaker.data.converters.AlbumTrackConverter
import com.example.playlistmaker.data.converters.TrackDbConverter
import com.example.playlistmaker.data.db.AppDatabase
import com.example.playlistmaker.data.history.HistoryRepository
import com.example.playlistmaker.data.library.AlbumListRepositoryImpl
import com.example.playlistmaker.data.library.FavoriteListRepositoryImpl
import com.example.playlistmaker.data.network.IApi
import com.example.playlistmaker.data.player.MediaPlayerRepositoryImpl
import com.example.playlistmaker.data.search.HistoryTrackListRepositoryImpl
import com.example.playlistmaker.data.search.SearchTrackListRepositoryImpl
import com.example.playlistmaker.data.search.state.SearchActivityStateRepositoryImpl
import com.example.playlistmaker.data.settings.ExternalNavigator
import com.example.playlistmaker.data.settings.SettingRepositoryImpl
import com.example.playlistmaker.domain.library.repositories.AlbumListRepository
import com.example.playlistmaker.domain.library.repositories.FavoriteListRepository
import com.example.playlistmaker.domain.player.repositories.MediaPlayerRepository
import com.example.playlistmaker.domain.player.repositories.TrackListRepository
import com.example.playlistmaker.domain.search.repository.HistoryTrackListRepository
import com.example.playlistmaker.domain.search.repository.SearchActivityStateRepository
import com.example.playlistmaker.domain.search.repository.SearchTrackListRepository
import com.example.playlistmaker.domain.settings.repository.SettingsRepository
import com.example.playlistmaker.domain.settings.repository.SharingRepoitory
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val retrofitModule = module {
    single {
        IApi(get())
    }

    single {
        Retrofit
            .Builder()
            .baseUrl("https://itunes.apple.com")
            .addConverterFactory(get())
            .build()
    }
    single<Converter.Factory> { GsonConverterFactory.create() }
}
val repoModule = module{
    single<HistoryTrackListRepository> { HistoryTrackListRepositoryImpl(get()) }
    single<SearchActivityStateRepository> { SearchActivityStateRepositoryImpl() }
    single<SearchTrackListRepository> { SearchTrackListRepositoryImpl(get()) }
    single { HistoryRepository(androidContext(),get())}


    single<SharingRepoitory> { ExternalNavigator(androidContext()) }
    single<SettingsRepository> { SettingRepositoryImpl(androidContext()) }

    single<MediaPlayerRepository> { MediaPlayerRepositoryImpl() }
    single<TrackListRepository> { HistoryRepository(androidContext(),get()) }

    factory { Gson() }

    single { Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
        .fallbackToDestructiveMigration()
        .build()}

    single<FavoriteListRepository>{ FavoriteListRepositoryImpl(get(), get())}

    factory { TrackDbConverter() }

    single<AlbumListRepository>{ AlbumListRepositoryImpl(get(),get(),get(),androidContext()) }
    factory { AlbumDbConverter() }
    factory { AlbumTrackConverter() }
}

