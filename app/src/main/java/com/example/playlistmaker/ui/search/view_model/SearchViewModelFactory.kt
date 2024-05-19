package com.example.playlistmaker.ui.search.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.create.CreatorSearchView

class SearchViewModelFactory(context: Context): ViewModelProvider.Factory {
    private val searchActivityStateInteractor by lazy {
        CreatorSearchView.provideSearchActivityStateInteractor()
    }
    private val historyTrackListInteractor by lazy {
        CreatorSearchView.provideHistoryTrackListInteractor(context.applicationContext)
    }
    private val searchTrackListInteractor by lazy {
        CreatorSearchView.provideSearchTrackListInteractor()
    }
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(historyTrackListInteractor, searchActivityStateInteractor, searchTrackListInteractor)as T
    }
}