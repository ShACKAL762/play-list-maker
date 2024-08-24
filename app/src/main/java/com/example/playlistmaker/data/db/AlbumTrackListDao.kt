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
    @Query("SELECT * FROM album_track_list_table ")
    suspend fun getTracks(): List<AlbumsTrackDbEntity>


    @Delete(entity = AlbumsTrackDbEntity::class)
    suspend fun deleteTrack(track: AlbumsTrackDbEntity)
    @Query("DELETE FROM album_track_list_table WHERE trackId =:trackId")
    suspend fun deleteTrack(trackId:String)
    @Query("DELETE FROM album_track_list_table WHERE albumId =:id")
    suspend fun deleteAllAlbumTracks(id: Int?)
    @Query("SELECT * FROM album_track_list_table WHERE trackId =:trackId ")
    fun getTrack(trackId: String) : AlbumsTrackDbEntity?
    @Query("SELECT `index` FROM album_track_list_table ORDER BY `index` DESC LIMIT 1")
    fun getMaxIndex():Long?

}