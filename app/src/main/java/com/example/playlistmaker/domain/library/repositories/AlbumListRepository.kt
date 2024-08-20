package com.example.playlistmaker.domain.library.repositories

import com.example.playlistmaker.domain.entity.Album
import com.example.playlistmaker.domain.entity.Track
import kotlinx.coroutines.flow.Flow

interface AlbumListRepository {
    fun getAlbumList(): Flow<List<Album>>

    suspend fun deleteAlbum(album: Album)
    suspend fun insertAlbum(album: Album): Long
    suspend fun addTrack(track: Track, albumId: Int):Long
}