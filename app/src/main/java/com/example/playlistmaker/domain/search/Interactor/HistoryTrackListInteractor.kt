package com.example.playlistmaker.domain.search.Interactor

import android.content.Context
import com.example.playlistmaker.data.entity.Track

interface HistoryTrackListInteractor {
    fun getTrackList():List<Track>
    fun getTrack(): Track
    fun setTrack(track: Track)
    fun clearHistory()

}