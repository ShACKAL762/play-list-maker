package com.example.playlistmaker.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.playlistmaker.data.db.entity.TrackDbEntity

@Dao
interface TrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(tracks: TrackDbEntity)

    @Query("SELECT * FROM track_table")
    suspend fun getTracks(): List<TrackDbEntity>

    @Query("SELECT trackId FROM track_table")
    suspend fun getTracksId(): List<String>
    @Delete(entity = TrackDbEntity::class)
    suspend fun deleteTrack(track:TrackDbEntity)
}