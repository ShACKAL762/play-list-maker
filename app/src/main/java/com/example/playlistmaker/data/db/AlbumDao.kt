package com.example.playlistmaker.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.playlistmaker.data.db.entity.AlbumDbEntity

@Dao
interface AlbumDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbum(albumDbEntity: AlbumDbEntity):Long
    
    @Query("SELECT * FROM album_table")
    suspend fun getAlbums(): List<AlbumDbEntity>
    @Update(onConflict = OnConflictStrategy.REPLACE, entity = AlbumDbEntity::class)
    suspend fun updateAlbum(albumDbEntity: AlbumDbEntity)
    @Query("SELECT * FROM album_table WHERE id = :albumId")
    suspend fun getAlbum(albumId: Int): AlbumDbEntity
    @Delete(entity = AlbumDbEntity::class)
    suspend fun delete(albumDbEntity: AlbumDbEntity)

    @Query("UPDATE album_table SET trackQuantity = :trackQuantity WHERE id = :albumId")
    fun updateTrackQuantity(albumId: Int, trackQuantity:Int)
    @Query("UPDATE album_table SET time = :timeQuantity WHERE id = :albumId")
    fun updateTimeQuantity(albumId: Int, timeQuantity:Long)

}
