package com.example.playlistmaker.domain.search.interactorImpl

import com.example.playlistmaker.domain.entity.SearchState
import com.example.playlistmaker.domain.search.interactor.SearchActivityStateInteractor
import com.example.playlistmaker.domain.search.repository.SearchActivityStateRepository
import com.example.playlistmaker.ui.search.view_model.SearchViewModel

class SearchActivityStateInteractorImpl(private val searchActivityStateRepository: SearchActivityStateRepository):SearchActivityStateInteractor {
    override fun changeState(state: SearchViewModel.SViewState): SearchState {
        return searchActivityStateRepository.changeState(state)
    }
}