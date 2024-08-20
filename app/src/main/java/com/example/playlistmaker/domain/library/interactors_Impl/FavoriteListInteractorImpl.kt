package com.example.playlistmaker.domain.library.interactors_Impl

import com.example.playlistmaker.domain.entity.Track
import com.example.playlistmaker.domain.library.interactors.FavoriteListInteractor
import com.example.playlistmaker.domain.library.repositories.FavoriteListRepository
import kotlinx.coroutines.flow.Flow

class FavoriteListInteractorImpl(private val favoriteListRepositoryImpl: FavoriteListRepository) :
    FavoriteListInteractor {
    override fun getFavoriteList(): Flow<List<Track>> {
        return favoriteListRepositoryImpl.getFavoriteList()
    }

    override fun getFavoriteIdList(): Flow<List<String>> {
        return favoriteListRepositoryImpl.getFavoriteIdList()
    }

    override suspend fun deleteTrack(track: Track) {
        favoriteListRepositoryImpl.deleteTrack(track)
    }

    override suspend fun insertTrack(track: Track) {
        favoriteListRepositoryImpl.insertTrack(track)
    }
}