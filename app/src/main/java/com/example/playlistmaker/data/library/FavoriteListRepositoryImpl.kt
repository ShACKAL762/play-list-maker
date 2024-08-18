package com.example.playlistmaker.data.library

import com.example.playlistmaker.data.converters.TrackDbConverter
import com.example.playlistmaker.data.db.AppDatabase
import com.example.playlistmaker.data.db.entity.TrackDbEntity
import com.example.playlistmaker.domain.entity.Track
import com.example.playlistmaker.domain.library.repositories.FavoriteListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavoriteListRepositoryImpl (
    private val appDatabase: AppDatabase,
    private val trackDbConvertor: TrackDbConverter,
): FavoriteListRepository {
    override fun getFavoriteList(): Flow<List<Track>> = flow {
        val tracks = appDatabase.trackDao().getTracks()
        emit(convertTracks(tracks))
    }

    override fun getFavoriteIdList(): Flow<List<String>> = flow {
        val tracks = appDatabase.trackDao().getTracksId()
        emit(tracks)
    }

    override suspend fun deleteTrack(track:Track) {
        appDatabase.trackDao().deleteTrack(trackDbConvertor.map(track))
    }

    override suspend fun insertTrack(track:Track) {
        appDatabase.trackDao().insertTrack(trackDbConvertor.map(track))
    }

    private fun convertTracks(tracks: List<TrackDbEntity>) : List<Track>{
        return tracks.map { track ->trackDbConvertor.map(track) }
    }
}