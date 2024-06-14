package com.example.playlistmaker.domain.search.Interactor

import com.example.playlistmaker.domain.entity.Track

interface HistoryTrackListInteractor {
    fun getTrackList():List<Track>
    fun getTrack(): Track
    fun setTrack(track: Track)
    fun clearHistory()

}