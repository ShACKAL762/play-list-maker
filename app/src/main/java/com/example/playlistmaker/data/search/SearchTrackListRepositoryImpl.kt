package com.example.playlistmaker.data.search


import com.example.playlistmaker.domain.entity.Resource
import com.example.playlistmaker.domain.entity.TrackList
import com.example.playlistmaker.data.network.IApi
import com.example.playlistmaker.domain.search.repository.SearchTrackListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchTrackListRepositoryImpl(private val api: IApi) : SearchTrackListRepository {
    override fun getTrackListResponse(request: String): Flow<Resource<TrackList>>
    = flow{
        emit(api.search(request))}

}
