package com.example.playlistmaker.data.search

import android.content.Context
import com.example.playlistmaker.data.history.HistoryRepository
import com.example.playlistmaker.data.entity.Track
import com.example.playlistmaker.domain.search.repository.HistoryTrackListRepository

class HistoryTrackListRepositoryImpl(val context: Context) : HistoryTrackListRepository {
    private val history = HistoryRepository(context)
    override fun getTrackList(): List<Track> {
        return history.getHistoryList()
    }

    override fun getTrack(): Track {
        return history.getHistoryList().first()
    }

    override fun setTrack(track: Track) {
        history.setHistory(track)
    }

    override fun cleanHistory() {
        history.clean()
    }
}