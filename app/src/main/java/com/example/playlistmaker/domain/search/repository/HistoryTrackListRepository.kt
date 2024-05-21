package com.example.playlistmaker.domain.search.repository

import android.content.Context
import com.example.playlistmaker.data.entity.Track

interface HistoryTrackListRepository {
    fun getTrackList():List<Track>
    fun getTrack(): Track
    fun setTrack(track: Track)
    fun cleanHistory()

}