package com.example.playlistmaker.ui.player

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivityPlayerBinding
import com.example.playlistmaker.domain.entity.Track
import java.text.SimpleDateFormat
import java.util.Locale

class PlayerHolder(private val binding:ActivityPlayerBinding) {



    private val timeFormat by lazy { SimpleDateFormat("mm:ss", Locale.getDefault()) }


    private val corner by lazy { binding.root.resources.getDimension(R.dimen.big_corner_radius).toInt() }



    fun bind(track: Track) {
        binding.trackName.text = track.trackName
        binding.trackArtistName.text = track.artistName
        binding.albumCount.text = track.collectionName
        binding.yearCount.text = track.releaseDate.removeRange(4,track.releaseDate.length)
        binding.styleCount.text = track.primaryGenreName
        binding.countryCount.text = track.country
        if (track.trackTimeMillis != null)
            binding.trackTime.text =
                timeFormat.format(track.trackTimeMillis.toInt())
        else binding.trackTime.text =
            binding.trackTime.resources.getText(R.string.default_track_time)

        Glide
            .with(binding.root)
            .load(getCoverArtwork(track.artworkUrl100))
            .centerCrop()
            .placeholder(R.drawable.placeholder)
            .transform(RoundedCorners(corner))
            .into(binding.trackImage)

    }
    private fun getCoverArtwork(artworkUrl100:String) = artworkUrl100.replaceAfterLast('/',"512x512bb.jpg")
}