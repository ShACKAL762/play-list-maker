package com.example.playlistmaker.presentation

import android.content.Intent
import android.os.*
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.ui.PlayerActivity
import com.example.playlistmaker.R
import com.example.playlistmaker.data.SearchHistory
import com.example.playlistmaker.presentation.models.Track

class SearchRecycleAdapter(private val list: List<Track>) : RecyclerView.Adapter<TrackViewHolder>() {
    companion object{
        const val CLICK_DEBOUNCE_DELAY = 500L
    }
    private var isClickAllowed = true
    private val handler = Handler(Looper.getMainLooper())
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_view, parent, false)
        return TrackViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val itemView = list[position]
        holder.bind(itemView)

        holder.itemView.setOnClickListener {
            if (isClickAllowed) {
                isClickAllowed = false

                handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
                SearchHistory().setHistory(it.context, itemView)
                it.context.startActivity(Intent(it.context, PlayerActivity::class.java))
            }
        }
    }

}
