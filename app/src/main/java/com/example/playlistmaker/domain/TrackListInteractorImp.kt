package com.example.playlistmaker.domain

import android.content.Context
import com.example.playlistmaker.data.TrackUseCaseRepoImp
import com.example.playlistmaker.domain.entity.Track

class TrackListInteractorImp(private val track: TrackUseCaseRepoImp): TrackListInteractor{
    override fun getTrackList(context: Context): List<Track> {
        return track.getTrackList(context)
    }

    override fun getTrack(context: Context): Track {
        return track.getTrack(context)
    }


}