package com.example.playlistmaker.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "album_table")
class AlbumDbEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String,
    val about: String,
    val trackQuantity: Int,
    val imageSrc: String,
    val time: Long
) {
}