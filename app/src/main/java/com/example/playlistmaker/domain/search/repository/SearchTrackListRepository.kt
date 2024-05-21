package com.example.playlistmaker.domain.search.repository

import com.example.playlistmaker.data.entity.TrackList
import retrofit2.Response

interface SearchTrackListRepository {
    fun getTrackListResponse(url: String, request:String) : Response<TrackList>

}