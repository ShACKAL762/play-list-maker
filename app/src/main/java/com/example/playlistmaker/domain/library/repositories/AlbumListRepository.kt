package com.example.playlistmaker.domain.library.repositories

import com.example.playlistmaker.domain.entity.Album
import com.example.playlistmaker.domain.entity.Track
import kotlinx.coroutines.flow.Flow

interface AlbumListRepository {
    fun getAlbumList(): Flow<List<Album>>
    fun getTrackList(albumId: Int): Flow<List<Track>>
    fun getAlbum(albumId: Int) : Flow<Album>
    suspend fun share(id: Int?)

    suspend fun deleteAlbum(album: Album)
    suspend fun insertAlbum(album: Album): Long
    suspend fun addTrack(track: Track, albumId: Int):Long
    suspend fun deleteTrack(track: Track, albumId: Int)
    suspend fun updateAlbum(album: Album)

}