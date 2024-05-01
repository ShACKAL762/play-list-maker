package com.example.playlistmaker.domain

import android.content.Context
import com.example.playlistmaker.domain.entity.Track

interface TrackListInteractor {
    fun getTrackList(context: Context):List<Track>
    fun getTrack(context: Context):Track

}