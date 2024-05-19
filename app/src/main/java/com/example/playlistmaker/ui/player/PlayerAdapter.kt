package com.example.playlistmaker.ui.player

import com.example.playlistmaker.databinding.ActivityPlayerBinding
import com.example.playlistmaker.domain.entity.Track

class PlayerAdapter(private val binding: ActivityPlayerBinding) {
    fun onBindPlayerHolder(track:Track){
        val holder = PlayerHolder(binding)
        holder.bind(track)
    }
}