package com.example.playlistmaker.data.search


import com.example.playlistmaker.data.entity.TrackList
import com.example.playlistmaker.data.network.IApi
import com.example.playlistmaker.domain.search.repository.SearchTrackListRepository
import retrofit2.Response

class SearchTrackListRepositoryImpl(private val api: IApi) : SearchTrackListRepository {
    override fun getTrackListResponse(request: String): Response<TrackList> {
        return api.search(request)
    }
}
