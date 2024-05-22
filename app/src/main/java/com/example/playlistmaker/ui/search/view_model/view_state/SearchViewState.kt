package com.example.playlistmaker.ui.search.view_model.view_state

import com.example.playlistmaker.data.entity.Track

sealed interface SearchViewState {

    object Loading : SearchViewState

    data class ShowHistory(
        val tracks: List<Track>
    ) : SearchViewState

    data class Content(
        val tracks: List<Track>
    ) : SearchViewState

    data class LostConnection(
        val errorMessage: String
    ) : SearchViewState

    object Empty : SearchViewState
    object HideHistory :SearchViewState
}
