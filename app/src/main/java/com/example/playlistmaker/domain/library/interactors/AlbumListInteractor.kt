package com.example.playlistmaker.domain.library.interactors

import com.example.playlistmaker.domain.entity.Album
import com.example.playlistmaker.domain.entity.Track
import kotlinx.coroutines.flow.Flow

interface AlbumListInteractor {
    fun getAlbumList(): Flow<List<Album>>
    fun getAlbum(albumId: Int): Flow<Album>

    fun getAlbumTrackList(albumId: Int): Flow<List<Track>>

    suspend fun deleteAlbum(album: Album)
    suspend fun insertAlbum(album: Album):Long
    suspend fun addTrack(trackId: Track, albumId: Int) :Long
    suspend fun deleteTrack(track: Track, albumId: Int)
    suspend fun updateAlbum(album: Album)

    suspend fun share(id: Int?)
    suspend fun getTrack(trackId: String): Flow<Track>
}