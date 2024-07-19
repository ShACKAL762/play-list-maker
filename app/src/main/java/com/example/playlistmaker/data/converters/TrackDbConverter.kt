package com.example.playlistmaker.data.converters

import com.example.playlistmaker.data.db.entity.TrackDbEntity
import com.example.playlistmaker.domain.entity.Track

class TrackDbConverter {
    fun map(track: Track):TrackDbEntity{
        return TrackDbEntity(
            track.trackId,
            track.trackName,
            track.artistName,
            track.trackTimeMillis,
            track.artworkUrl100,
            track.collectionName,
            track.releaseDate,
            track.primaryGenreName,
            track.country,
            track.previewUrl,
            track.isFavorite)
    }

    fun map(trackDbEntity: TrackDbEntity):Track{
        return Track(
            trackDbEntity.trackName,
            trackDbEntity.artistName,
            trackDbEntity.trackTimeMillis,
            trackDbEntity.artworkUrl100,
            trackDbEntity.collectionName,
            trackDbEntity.releaseDate,
            trackDbEntity.primaryGenreName,
            trackDbEntity.country,
            trackDbEntity.trackId,
            trackDbEntity.previewUrl,
            trackDbEntity.isFavorite
        )
    }
}