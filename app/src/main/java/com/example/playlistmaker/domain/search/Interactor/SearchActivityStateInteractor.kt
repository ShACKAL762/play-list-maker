package com.example.playlistmaker.domain.search.Interactor

import com.example.playlistmaker.data.search.state.SearchState
import com.example.playlistmaker.ui.search.view_model.SearchViewModel

interface SearchActivityStateInteractor {
    fun changeState(state: SearchViewModel.SViewState): SearchState

}