package com.example.playlistmaker.domain.search.Interactor

import com.example.playlistmaker.domain.entity.TrackList
import retrofit2.Response

interface SearchTrackListInteractor {
    fun getTrackListResponse(url: String, request:String) : Response<TrackList>

}