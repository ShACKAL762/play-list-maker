package com.example.playlistmaker.player

import com.example.playlistmaker.search.Track

class PlayerAdapter(private val context: PlayerActivity) {
    fun onBindPlayerHolder(list: List<Track>){
        var holder = PlayerHolder(context)
        holder.bind(list.first())
    }
}