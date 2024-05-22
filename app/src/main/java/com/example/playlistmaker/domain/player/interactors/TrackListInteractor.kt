package com.example.playlistmaker.domain.player.interactors

import android.content.Context
import com.example.playlistmaker.data.entity.Track

interface TrackListInteractor {
    fun getTrackList():List<Track>
    fun getTrack(): Track

}