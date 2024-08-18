package com.example.playlistmaker.ui.player.view_model.recycle_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.databinding.AlbumViewBinding
import com.example.playlistmaker.domain.entity.Album
import com.example.playlistmaker.ui.library.view_models.recycleView.AlbumRecycleAdapter

class PlayerRecycleAdapter(
    private val list: List<Album>,
    private val clickListener: AlbumRecycleAdapter.TrackClickListener
) :
    RecyclerView.Adapter<PlayerRecycleViewHolder>() {

    fun interface TrackClickListener {
        fun onTrackClick(album: Album)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerRecycleViewHolder {
        val view = LayoutInflater.from(parent.context)
        return PlayerRecycleViewHolder(AlbumViewBinding.inflate(view, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PlayerRecycleViewHolder, position: Int) {
        val itemView = list[position]
        holder.bind(itemView)

        holder.itemView.setOnClickListener {
            clickListener.onTrackClick(itemView)
        }
    }
}