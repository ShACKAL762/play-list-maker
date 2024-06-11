package com.example.playlistmaker.data.search


import com.example.playlistmaker.data.entity.TrackList
import com.example.playlistmaker.data.network.IApi
import com.example.playlistmaker.domain.search.repository.SearchTrackListRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Response

class SearchTrackListRepositoryImpl : SearchTrackListRepository, KoinComponent {
    private val api: IApi by inject()
    override fun getTrackListResponse(request: String): Response<TrackList> {
        return api.search(request)
    }
}
