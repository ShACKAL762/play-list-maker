package com.example.playlistmaker.domain.library.repositories

import com.example.playlistmaker.domain.entity.Track
import kotlinx.coroutines.flow.Flow

interface FavoriteListRepository {
    fun getFavoriteList(): Flow<List<Track>>
    fun getFavoriteIdList(): Flow<List<String>>
    suspend fun deleteTrack(track: Track)
    suspend fun insertTrack(track: Track)

}