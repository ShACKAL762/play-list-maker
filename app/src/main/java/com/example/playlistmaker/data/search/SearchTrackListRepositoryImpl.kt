package com.example.playlistmaker.data.search

import com.example.playlistmaker.data.network.IApi
import com.example.playlistmaker.domain.entity.Track
import com.example.playlistmaker.domain.search.repository.SearchTrackListRepository
import com.example.playlistmaker.ui.models.TrackList
import retrofit2.Response

class SearchTrackListRepositoryImpl : SearchTrackListRepository {
    override fun getTrackListResponse(url: String, request: String): Response<TrackList> {
        return IApi(url).search(request)
    }
}
