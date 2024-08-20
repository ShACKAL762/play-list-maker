package com.example.playlistmaker.ui.library.view_models.recycleView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.databinding.AlbumCardViewBinding
import com.example.playlistmaker.domain.entity.Album

class AlbumRecycleAdapter(
    private val list: List<Album>,
    private val clickListener: TrackClickListener
) :
    RecyclerView.Adapter<AlbumTrackViewHolder>() {
    fun interface TrackClickListener {
        fun onTrackClick(album: Album)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumTrackViewHolder {
        val view = LayoutInflater.from(parent.context)
        return AlbumTrackViewHolder(AlbumCardViewBinding.inflate(view, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AlbumTrackViewHolder, position: Int) {
        val itemView = list[position]
        holder.bind(itemView)

        holder.itemView.setOnClickListener {
            clickListener.onTrackClick(itemView)
        }

    }
}
