package com.example.playlistmaker.data

import android.content.Context
import com.example.playlistmaker.data.search.SearchHistoryRepository
import com.example.playlistmaker.domain.repositories.TrackListRepository
import com.example.playlistmaker.domain.entity.Track

class TrackListRepositoryImpl: TrackListRepository {
    private val history = SearchHistoryRepository()
    override fun getTrackList(context: Context): List<Track> {
        return history.getHistoryList(context)
    }

    override fun getTrack(context: Context): Track {
        return history.getHistoryList(context).first()
    }
}