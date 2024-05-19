package com.example.playlistmaker.data

import android.content.Context
import com.example.playlistmaker.data.search.SearchHistoryRepository
import com.example.playlistmaker.domain.repositories.TrackListRepository
import com.example.playlistmaker.domain.entity.Track

class TrackListRepositoryImpl(context: Context): TrackListRepository {
    private val history = SearchHistoryRepository(context)
    override fun getTrackList(): List<Track> {
        return history.getHistoryList()
    }

    override fun getTrack(): Track {
        return history.getHistoryList().first()
    }
}