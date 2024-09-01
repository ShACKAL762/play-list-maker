package com.example.playlistmaker.ui.playlist.recycleView

import com.example.playlistmaker.domain.entity.Track

interface TrackClickListener {
    fun onLongTrackClick(track: Track):Boolean
    fun onTrackClick(track: Track)
}