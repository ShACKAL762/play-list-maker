package com.example.playlistmaker.domain.player.interactors

import android.content.Context
import com.example.playlistmaker.domain.entity.Track

interface TrackListInteractor {
    fun getTrackList():List<Track>
    fun getTrack():Track

}