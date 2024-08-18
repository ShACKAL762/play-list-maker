package com.example.playlistmaker.ui.library.view_models

import android.net.Uri

class CreateAlbumState {
    var albumName: String = ""
    var about: String = ""
    var imageSrc: Uri = Uri.EMPTY
    var trackList: List<String> = emptyList()
}