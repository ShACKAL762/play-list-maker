package com.example.playlistmaker.data.converters

import com.example.playlistmaker.data.db.entity.AlbumDbEntity
import com.example.playlistmaker.domain.entity.Album

class AlbumDbConverter {
    fun map(album: Album): AlbumDbEntity {
        return AlbumDbEntity(
            album.id,
            album.name,
            album.about,
            album.tracksQuantity,
            album.imageSrc.toString()
        )
    }

    fun map(albumDbEntity: AlbumDbEntity): Album {
        return Album(
            albumDbEntity.id,
            albumDbEntity.name,
            albumDbEntity.about,
            albumDbEntity.trackQuantity,
            albumDbEntity.imageSrc
        )
    }
}
