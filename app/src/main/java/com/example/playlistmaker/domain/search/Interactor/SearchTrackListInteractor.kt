package com.example.playlistmaker.domain.search.Interactor

import com.example.playlistmaker.data.entity.Resource
import com.example.playlistmaker.domain.entity.TrackList
import kotlinx.coroutines.flow.Flow

interface SearchTrackListInteractor {
    suspend fun getTrackListResponse(url: String, request:String) : Flow<Resource<TrackList>>

}