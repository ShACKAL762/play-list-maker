package com.example.playlistmaker.domain.entity

class Album(
    val id: Int?,
    val name: String,
    val about:String,
    val tracksQuantity:Int,
    var imageSrc: String?,
)