package com.example.playlistmaker.ui.library.view_models.recycleView

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.AlbumCardViewBinding
import com.example.playlistmaker.domain.entity.Album
import com.example.playlistmaker.utilities.trackFormat

class AlbumTrackViewHolder(private val binding:AlbumCardViewBinding) : RecyclerView.ViewHolder(binding.root){
    private val corner by lazy { itemView.resources.getDimension(R.dimen.album_corner_radius).toInt() }



    fun bind(album : Album) {
        binding.cardAlbumName.text = album.name

        val trackFormatText = "${album.tracksQuantity} ${trackFormat(album.tracksQuantity,itemView.context)}"
        binding.tracksQuantity.text = trackFormatText

        Glide
            .with(itemView)
            .load(album.imageSrc)
            .placeholder(R.drawable.placeholder)
            .centerCrop()
            .transform(RoundedCorners(corner))
            .into(binding.cardImage)

    }


}
