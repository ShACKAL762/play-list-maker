package com.example.playlistmaker.domain.player.interactors_impl

import android.content.Context
import com.example.playlistmaker.domain.repositories.TrackListRepository
import com.example.playlistmaker.domain.entity.Track
import com.example.playlistmaker.domain.player.interactors.TrackListInteractor

class TrackListInteractorImpl(private val track: TrackListRepository): TrackListInteractor {
    override fun getTrackList(context: Context): List<Track> {
        return track.getTrackList(context)
    }

    override fun getTrack(context: Context): Track {
        return track.getTrack(context)
    }


}