package com.example.playlistmaker.domain.library.interactors

import com.example.playlistmaker.domain.entity.Album
import com.example.playlistmaker.domain.entity.Track
import kotlinx.coroutines.flow.Flow

interface AlbumListInteractor {
    fun getAlbumList(): Flow<List<Album>>

    suspend fun deleteAlbum(album: Album)
    suspend fun insertAlbum(album: Album):Long
    suspend fun addTrack(trackId: Track, albumId: Int) :Long

}