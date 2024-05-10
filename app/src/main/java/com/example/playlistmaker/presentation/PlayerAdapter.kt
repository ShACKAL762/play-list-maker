package com.example.playlistmaker.presentation

import com.example.playlistmaker.domain.entity.Track
import com.example.playlistmaker.presentation.ui.PlayerActivity

class PlayerAdapter(private val context: PlayerActivity) {
    fun onBindPlayerHolder(track:Track){
        val holder = PlayerHolder(context)
        holder.bind(track)
    }
}