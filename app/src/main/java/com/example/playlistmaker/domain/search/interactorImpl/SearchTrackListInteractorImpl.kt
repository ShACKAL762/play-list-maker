package com.example.playlistmaker.domain.search.interactorImpl

import com.example.playlistmaker.domain.entity.Resource
import com.example.playlistmaker.domain.search.interactor.SearchTrackListInteractor
import com.example.playlistmaker.domain.search.repository.SearchTrackListRepository
import com.example.playlistmaker.domain.entity.TrackList
import kotlinx.coroutines.flow.Flow


class SearchTrackListInteractorImpl(val searchTrackListRepository: SearchTrackListRepository) :
    SearchTrackListInteractor {
    override fun getTrackListResponse(url: String, request: String
    ): Flow<Resource<TrackList>> =
        searchTrackListRepository.getTrackListResponse(request)
}
