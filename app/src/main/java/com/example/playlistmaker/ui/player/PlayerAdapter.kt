package com.example.playlistmaker.ui.player

import com.example.playlistmaker.domain.entity.Track
import com.example.playlistmaker.ui.player.activity.PlayerActivity

class PlayerAdapter(private val context: PlayerActivity) {
    fun onBindPlayerHolder(track:Track){
        val holder = PlayerHolder(context)
        holder.bind(track)
    }
}