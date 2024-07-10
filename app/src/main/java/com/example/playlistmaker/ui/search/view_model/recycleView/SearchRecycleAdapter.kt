package com.example.playlistmaker.ui.search.view_model.recycleView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.databinding.TrackViewBinding
import com.example.playlistmaker.domain.entity.Track

class SearchRecycleAdapter(
    private val list: List<Track>,
    private val clickListener: TrackClickListener
) :
    RecyclerView.Adapter<TrackViewHolder>() {
    fun interface TrackClickListener {
        fun onTrackClick(track: Track)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context)
        return TrackViewHolder(TrackViewBinding.inflate(view, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val itemView = list[position]
        holder.bind(itemView)

        holder.itemView.setOnClickListener {
            clickListener.onTrackClick(itemView)
        }

    }
}
