package com.example.playlistmaker.di


import android.content.Context
import com.example.playlistmaker.data.main.MainMenuReopsitoryImpl
import com.example.playlistmaker.data.settings.ExternalNavigator
import com.example.playlistmaker.domain.main.iteractor_impl.MainMenuInteractorImpl
import com.example.playlistmaker.domain.settings.interactor_impl.SharingInteractorImpl
import com.example.playlistmaker.ui.main.view_model.MainActivityViewModel
import com.example.playlistmaker.ui.player.view_model.PlayerViewModel
import com.example.playlistmaker.ui.search.view_model.SearchViewModel
import com.example.playlistmaker.ui.settings.view_model.SettingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModules = module {
    viewModel { SearchViewModel(get(), get(), get()) }

    viewModel {(context: Context) -> MainActivityViewModel(MainMenuInteractorImpl(MainMenuReopsitoryImpl(context))) } //Если скажите как пробросить контекст активити иначе, буду очень благодарен.

    viewModel {(context: Context) -> SettingViewModel(SharingInteractorImpl(ExternalNavigator(context)), get()) }//Если скажите как пробросить контекст активити иначе, буду очень благодарен.

    viewModel { PlayerViewModel(get(), get()) }
}
