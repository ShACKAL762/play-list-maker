package com.example.playlistmaker.domain.player.repositories

import com.example.playlistmaker.domain.entity.Track

interface TrackListRepository {
    fun getTrackList():List<Track>
    fun getTrack(): Track?

}