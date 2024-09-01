package com.example.playlistmaker.domain.player.interactors_impl

import com.example.playlistmaker.domain.entity.Track
import com.example.playlistmaker.domain.player.interactors.TrackListInteractor
import com.example.playlistmaker.domain.player.repositories.TrackListRepository

class TrackListInteractorImpl(private val track: TrackListRepository): TrackListInteractor {
    override fun getTrackList(): List<Track> {
        return track.getTrackList()
    }

    override fun getTrack(): Track? {
        return track.getTrack()
    }


}