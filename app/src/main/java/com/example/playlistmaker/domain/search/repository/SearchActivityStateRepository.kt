package com.example.playlistmaker.domain.search.repository

import com.example.playlistmaker.domain.entity.SearchState
import com.example.playlistmaker.ui.search.view_model.SearchViewModel

interface SearchActivityStateRepository {
    fun changeState(state: SearchViewModel.SViewState): SearchState
}