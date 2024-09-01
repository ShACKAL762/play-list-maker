package com.example.playlistmaker.ui.player.view_model.recycle_view

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.AlbumViewBinding
import com.example.playlistmaker.domain.entity.Album
import com.example.playlistmaker.utilities.trackFormat

class PlayerRecycleViewHolder(private val binding: AlbumViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private val corner by lazy { itemView.resources.getDimension(R.dimen.corner_radius).toInt() }


    fun bind(album: Album) {
        binding.albumName.text = album.name

        val trackQuantityFormat =
            "${album.tracksQuantity} ${trackFormat(album.tracksQuantity, itemView.context)}"
        binding.tracksQuantity.text = trackQuantityFormat

        Glide
            .with(itemView)
            .load(album.imageSrc)
            .centerCrop()
            .placeholder(R.drawable.placeholder)
            .transform(RoundedCorners(corner))
            .into(binding.trackImage)
    }
}