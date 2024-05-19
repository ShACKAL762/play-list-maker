package com.example.playlistmaker.data

import android.content.Context
import com.example.playlistmaker.data.history.HistoryRepository
import com.example.playlistmaker.domain.repositories.TrackListRepository
import com.example.playlistmaker.domain.entity.Track

class TrackListRepositoryImpl(context: Context): TrackListRepository {
    private val history = HistoryRepository(context)
    override fun getTrackList(): List<Track> {
        return history.getHistoryList()
    }

    override fun getTrack(): Track {
        return history.getHistoryList().first()
    }
}