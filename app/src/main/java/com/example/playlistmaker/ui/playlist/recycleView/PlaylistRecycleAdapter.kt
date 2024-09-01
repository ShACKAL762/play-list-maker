package com.example.playlistmaker.ui.search.recycleView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.databinding.TrackViewBinding
import com.example.playlistmaker.domain.entity.Track
import com.example.playlistmaker.ui.playlist.recycleView.TrackClickListener

class PlaylistRecycleAdapter(
    private val list: List<Track>,
    private val clickListener: TrackClickListener
) :
    RecyclerView.Adapter<PlaylistTrackViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistTrackViewHolder {
        val view = LayoutInflater.from(parent.context)
        return PlaylistTrackViewHolder(TrackViewBinding.inflate(view, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PlaylistTrackViewHolder, position: Int) {
        val itemView = list[position]
        holder.bind(itemView)

        holder.itemView.setOnClickListener {
            clickListener.onTrackClick(itemView)
        }
        holder.itemView.setOnLongClickListener{
            clickListener.onLongTrackClick(itemView)
        }

    }
}
