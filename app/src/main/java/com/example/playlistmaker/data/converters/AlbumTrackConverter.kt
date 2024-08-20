package com.example.playlistmaker.data.converters

import com.example.playlistmaker.data.db.entity.AlbumsTrackDbEntity
import com.example.playlistmaker.domain.entity.Track

class AlbumTrackConverter {
    fun map(track: Track, albumId: Int): AlbumsTrackDbEntity {
        return AlbumsTrackDbEntity(
            albumId,
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
            track.isFavorite
        )
    }

    fun map(albumsTrackDbEntity: AlbumsTrackDbEntity): Track {
        return Track(
            albumsTrackDbEntity.trackName,
            albumsTrackDbEntity.artistName,
            albumsTrackDbEntity.trackTimeMillis,
            albumsTrackDbEntity.artworkUrl100,
            albumsTrackDbEntity.collectionName,
            albumsTrackDbEntity.releaseDate,
            albumsTrackDbEntity.primaryGenreName,
            albumsTrackDbEntity.country,
            albumsTrackDbEntity.trackId,
            albumsTrackDbEntity.previewUrl,
            albumsTrackDbEntity.isFavorite
        )
    }

}