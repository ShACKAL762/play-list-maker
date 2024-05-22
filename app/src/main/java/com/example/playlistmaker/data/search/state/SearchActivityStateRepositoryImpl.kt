package com.example.playlistmaker.data.search.state

import com.example.playlistmaker.domain.search.repository.SearchActivityStateRepository
import com.example.playlistmaker.ui.search.view_model.SearchViewModel
import com.example.playlistmaker.ui.search.view_model.SearchViewModel.SViewState.*

class SearchActivityStateRepositoryImpl : SearchActivityStateRepository {
    override fun changeState(state: SearchViewModel.SViewState): SearchState {
        return when (state) {
            SUCCESS -> SearchState.SUCCESS
            LOADING -> SearchState.LOADING
            NOT_FOUND -> SearchState.NOT_FOUND
            LOST_CONNECTION -> SearchState.LOST_CONNECTION
            SHOW_HISTORY -> SearchState.SHOW_HISTORY
            HIDE_HISTORY -> SearchState.HIDE_HISTORY
            DEFAULT -> SearchState.DEFAULT
        }
    }
}