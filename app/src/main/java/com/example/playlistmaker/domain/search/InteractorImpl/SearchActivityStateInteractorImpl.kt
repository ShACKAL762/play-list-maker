package com.example.playlistmaker.domain.search.InteractorImpl

import com.example.playlistmaker.data.search.state.SearchState
import com.example.playlistmaker.domain.search.Interactor.SearchActivityStateInteractor
import com.example.playlistmaker.domain.search.repository.SearchActivityStateRepository
import com.example.playlistmaker.ui.search.view_model.SearchViewModel

class SearchActivityStateInteractorImpl(private val searchActivityStateRepository: SearchActivityStateRepository):SearchActivityStateInteractor {
    override fun changeState(state: SearchViewModel.SViewState): SearchState {
        return searchActivityStateRepository.changeState(state)
    }
}