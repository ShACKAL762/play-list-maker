package com.example.playlistmaker.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.playlistmaker.data.db.entity.AlbumsTrackDbEntity

@Dao
interface AlbumTrackListDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrack(tracks: AlbumsTrackDbEntity) :Long

    @Query("SELECT * FROM album_track_list_table WHERE albumId =:albumId ")
    suspend fun getTracks(albumId:Int): List<AlbumsTrackDbEntity>

    @Query("SELECT trackId FROM album_track_list_table")
    suspend fun getTracksId(): List<String>

    @Delete(entity = AlbumsTrackDbEntity::class)
    suspend fun deleteTrack(track: AlbumsTrackDbEntity)

}