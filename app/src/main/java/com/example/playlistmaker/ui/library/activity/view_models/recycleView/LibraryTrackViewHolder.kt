package com.example.playlistmaker.ui.search.view_model.recycleView

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.TrackViewBinding
import com.example.playlistmaker.domain.entity.Track
import java.text.SimpleDateFormat
import java.util.Locale

class LibraryTrackViewHolder(private val binding:TrackViewBinding) : RecyclerView.ViewHolder(binding.root) {

    private val dateFormat by lazy { SimpleDateFormat("mm:ss", Locale.getDefault()) }
    private val corner by lazy { itemView.resources.getDimension(R.dimen.corner_radius).toInt() }



    fun bind(track: Track) {
        binding.trackName.text = track.trackName
        binding.authorName.text = track.artistName
        if (track.trackTimeMillis != null){
            binding.time.text =
                dateFormat.format(track.trackTimeMillis.toInt())}
        else binding.time.text =
            binding.time.resources.getText(R.string.default_track_time)

        Glide
            .with(itemView)
            .load(track.artworkUrl100)
            .fitCenter()
            .placeholder(R.drawable.placeholder)
            .transform(RoundedCorners(corner))
            .into(binding.trackImage)

    }


}
