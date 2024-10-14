package com.example.playlistmaker.ui.playlist

import com.example.playlistmaker.databinding.PlaylistFragmentBinding
import com.example.playlistmaker.domain.entity.Album

class PlayListAdapter (private val binding: PlaylistFragmentBinding) {

    fun onBindPlayerHolder(album: Album){
        val holder = PlayListHolder(binding)
        holder.bind(album)
    }
}