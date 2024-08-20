package com.example.playlistmaker.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.playlistmaker.data.db.entity.AlbumDbEntity

@Dao
interface AlbumDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbum(albumDbEntity: AlbumDbEntity):Long

    /*@Query("SELECT trackList FROM album_table WHERE name = :albumName")
    suspend fun getTrackList(albumName:String): String*/
    @Query("SELECT * FROM album_table")
    suspend fun getAlbums(): List<AlbumDbEntity>
    @Delete(entity = AlbumDbEntity::class)
    suspend fun delete(albumDbEntity: AlbumDbEntity)

    @Query("UPDATE album_table SET trackQuantity = :trackQuantity WHERE id = :albumId")
    fun updateTrackQuantity(albumId: Int, trackQuantity:Int)

}
