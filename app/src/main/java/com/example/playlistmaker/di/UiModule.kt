package com.example.playlistmaker.di


import com.example.playlistmaker.ui.library.activity.view_models.AlbumsListViewModel
import com.example.playlistmaker.ui.library.activity.view_models.FavoriteListFragmentViewModel
import com.example.playlistmaker.ui.main.view_model.MainActivityViewModel
import com.example.playlistmaker.ui.player.view_model.PlayerViewModel
import com.example.playlistmaker.ui.search.view_model.SearchViewModel
import com.example.playlistmaker.ui.settings.view_model.SettingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModules = module {
    viewModel { SearchViewModel(get(), get(), get()) }

    viewModel {MainActivityViewModel(get())}

    viewModel {SettingViewModel(get(),get())}

    viewModel { PlayerViewModel(get(), get()) }

    viewModel { AlbumsListViewModel()}

    viewModel{ FavoriteListFragmentViewModel() }
}
