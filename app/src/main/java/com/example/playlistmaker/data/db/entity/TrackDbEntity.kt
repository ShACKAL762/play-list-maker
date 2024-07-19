package com.example.playlistmaker.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "track_table")
class TrackDbEntity(
    @PrimaryKey
    var trackId: String,
    var trackName: String, // Название композиции)
    var artistName: String, // Имя исполнителя)
    var trackTimeMillis: String, // Продолжительность трека)
    var artworkUrl100: String, // Ссылка на изображение обложки)
    var collectionName: String, // Название альбома
    var releaseDate: String, // Год релиза
    var primaryGenreName: String, // Жанр
    var country: String, // Страна
    var previewUrl: String,
    var isFavorite: Boolean
)