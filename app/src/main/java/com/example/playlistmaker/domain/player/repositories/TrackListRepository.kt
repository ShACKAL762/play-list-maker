package com.example.playlistmaker.domain.player.repositories

import android.content.Context
import com.example.playlistmaker.data.entity.Track

interface TrackListRepository {
    fun getTrackList():List<Track>
    fun getTrack(): Track

}