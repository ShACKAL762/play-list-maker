package com.example.playlistmaker.domain.repositories

import android.content.Context
import com.example.playlistmaker.domain.entity.Track

interface TrackListRepository {
    fun getTrackList(context: Context):List<Track>
    fun getTrack(context: Context): Track

}