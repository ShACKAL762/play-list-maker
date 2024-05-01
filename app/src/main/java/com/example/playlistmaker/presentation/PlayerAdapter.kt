package com.example.playlistmaker.presentation

import com.example.playlistmaker.presentation.models.Track
import com.example.playlistmaker.ui.PlayerActivity

class PlayerAdapter(private val context: PlayerActivity) {
    fun onBindPlayerHolder(list: List<Track>){
        var holder = PlayerHolder(context)
        holder.bind(list.first())
    }
}