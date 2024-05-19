package com.example.playlistmaker.ui.search.view_model.recycleView

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.data.history.HistoryRepository
import com.example.playlistmaker.databinding.TrackViewBinding
import com.example.playlistmaker.domain.entity.Track
import com.example.playlistmaker.ui.player.activity.PlayerActivity

class SearchRecycleAdapter(private val list: List<Track>) : RecyclerView.Adapter<TrackViewHolder>() {
    companion object{
        const val TRACK_ID = "TRACK_ID"
        const val CLICK_DEBOUNCE_DELAY = 500L
    }
    private var isClickAllowed = true
    private val handler = Handler(Looper.getMainLooper())
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
            if (isClickAllowed) {
                isClickAllowed = false

                handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
                HistoryRepository(it.context).setHistory( itemView)
                val intent = Intent(it.context, PlayerActivity::class.java).putExtra(TRACK_ID, itemView.trackId)
                it.context.startActivity(intent)
            }
        }
    }

}
