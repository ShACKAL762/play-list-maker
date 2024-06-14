package com.example.playlistmaker.domain.search.repository

import com.example.playlistmaker.domain.entity.TrackList
import retrofit2.Response

interface SearchTrackListRepository {
    fun getTrackListResponse(request:String) : Response<TrackList>

}