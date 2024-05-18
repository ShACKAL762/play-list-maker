package com.example.playlistmaker.domain.search.repository

import com.example.playlistmaker.domain.entity.Track
import com.example.playlistmaker.ui.models.TrackList
import retrofit2.Response

interface SearchTrackListRepository {
    fun getTrackListResponse(url: String, request:String) : Response<TrackList>

}