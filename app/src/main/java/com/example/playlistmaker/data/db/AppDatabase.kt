package com.example.playlistmaker.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.playlistmaker.data.db.entity.AlbumDbEntity
import com.example.playlistmaker.data.db.entity.AlbumsTrackDbEntity
import com.example.playlistmaker.data.db.entity.TrackDbEntity

@Database(version = 2, entities = [TrackDbEntity::class,AlbumDbEntity::class,AlbumsTrackDbEntity::class], exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trackDao(): TrackDao
    abstract fun albumDao(): AlbumDao
    abstract fun albumTrackListDao(): AlbumTrackListDao
}