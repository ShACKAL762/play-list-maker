package com.example.playlistmaker.ui.playlist

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.PlaylistFragmentBinding
import com.example.playlistmaker.domain.entity.Album
import com.example.playlistmaker.utilities.minuteFormat
import com.example.playlistmaker.utilities.trackFormat
import java.text.SimpleDateFormat
import java.util.Locale

class PlayListHolder (private val binding: PlaylistFragmentBinding){
    private val corner by lazy { binding.root.resources.getDimension(R.dimen.corner_radius).toInt() }
    private val timeFormat by lazy { SimpleDateFormat("m", Locale.getDefault()) }
    fun bind(album: Album) {
        val trackQuantity = "${album.tracksQuantity} ${trackFormat(album.tracksQuantity,binding.root.context)}"
        val minuteQuantity = "${timeFormat.format(album.time)} ${minuteFormat(timeFormat.format(album.time).toInt(),binding.root.context)}"
        binding.name.text = album.name
        binding.about.text = album.about
        binding.tracksQuantity.text = trackQuantity
        binding.timeQuantity.text = minuteQuantity

        binding.tracksQuantityMenu.text = trackQuantity
        binding.albumName.text = album.name


        Glide
            .with(binding.root)
            .load(album.imageSrc)
            .centerCrop()
            .placeholder(R.drawable.placeholder)
            .into(binding.albumImage)
        Glide
            .with(binding.root)
            .load(album.imageSrc)
            .centerCrop()
            .transform(RoundedCorners(corner))
            .placeholder(R.drawable.placeholder)
            .into(binding.menuAlbumImage)


    }
}