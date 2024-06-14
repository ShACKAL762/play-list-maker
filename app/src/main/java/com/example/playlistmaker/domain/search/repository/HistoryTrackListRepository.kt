package com.example.playlistmaker.domain.search.repository

import com.example.playlistmaker.domain.entity.Track

interface HistoryTrackListRepository {
    fun getTrackList():List<Track>
    fun getTrack(): Track
    fun setTrack(track: Track)
    fun cleanHistory()

}