package com.example.playlistmaker.domain.search.repository

import com.example.playlistmaker.data.entity.Resource
import com.example.playlistmaker.domain.entity.TrackList
import kotlinx.coroutines.flow.Flow

interface SearchTrackListRepository {
    suspend fun getTrackListResponse(request:String) : Flow<Resource<TrackList>>

}