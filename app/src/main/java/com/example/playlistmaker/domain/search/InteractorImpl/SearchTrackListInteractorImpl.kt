package com.example.playlistmaker.domain.search.InteractorImpl

import com.example.playlistmaker.domain.entity.Track
import com.example.playlistmaker.domain.search.Interactor.SearchTrackListInteractor
import com.example.playlistmaker.domain.search.repository.SearchTrackListRepository
import com.example.playlistmaker.ui.models.TrackList
import retrofit2.Response

class SearchTrackListInteractorImpl(val searchTrackListRepository: SearchTrackListRepository):SearchTrackListInteractor {
    override fun getTrackListResponse(url: String, request:String): Response<TrackList> {
        return searchTrackListRepository.getTrackListResponse(url,request)
    }



}