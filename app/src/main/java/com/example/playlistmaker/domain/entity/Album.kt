package com.example.playlistmaker.domain.entity

import android.net.Uri

class Album(
    val id: Int?,
    val name: String,
    val about:String,
    val tracksQuantity:Int,
    var imageSrc: Uri? = Uri.EMPTY,
)