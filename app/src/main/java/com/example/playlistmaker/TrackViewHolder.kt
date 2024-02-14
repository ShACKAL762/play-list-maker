package com.example.playlistmaker

import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.util.zip.Inflater

class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var trackImageView: ImageView
    private var trackArtistName: TextView
    private var trackName: TextView
    private var trackTime: TextView

    init {
        trackImageView = itemView.findViewById(R.id.track_image)
        trackArtistName = itemView.findViewById(R.id.author_name)
        trackName = itemView.findViewById(R.id.track_name)
        trackTime = itemView.findViewById(R.id.track_time)
    }
    fun bind(track: Track){
        trackName.text = track.trackName
        trackArtistName.text = track.artistName
        trackTime.text = track.trackTime
        Glide
            .with(itemView)
            .load(track.artworkUrl100)
            .fitCenter()
            .placeholder(R.drawable.placeholder)
            .transform(RoundedCorners(2))
            .into(trackImageView)

    }



}
