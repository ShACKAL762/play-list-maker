package com.example.playlistmaker.ui.player

import com.example.playlistmaker.databinding.ActivityPlayerBinding
import com.example.playlistmaker.data.entity.Track

class PlayerAdapter(private val binding: ActivityPlayerBinding) {
    fun onBindPlayerHolder(track: Track){
        val holder = PlayerHolder(binding)
        holder.bind(track)
    }
}