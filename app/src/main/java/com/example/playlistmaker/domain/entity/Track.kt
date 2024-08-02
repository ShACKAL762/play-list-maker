package com.example.playlistmaker.domain.entity

class Track(
    var trackName: String, // Название композиции)
    var artistName: String, // Имя исполнителя)
    var trackTimeMillis: String, // Продолжительность трека)
    var artworkUrl100: String, // Ссылка на изображение обложки)
    var collectionName: String, // Название альбома
    var releaseDate: String, // Год релиза
    var primaryGenreName: String, // Жанр
    var country: String, // Страна
    var trackId: String,
    var previewUrl : String,
    var isFavorite: Boolean = false

) 