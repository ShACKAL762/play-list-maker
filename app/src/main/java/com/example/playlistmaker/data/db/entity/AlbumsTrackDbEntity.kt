package com.example.playlistmaker.data.db.entity

import androidx.room.Entity

@Entity(tableName = "album_track_list_table", primaryKeys = ["albumId", "trackId"])
class AlbumsTrackDbEntity(
    var albumId: Int,
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
) {
}