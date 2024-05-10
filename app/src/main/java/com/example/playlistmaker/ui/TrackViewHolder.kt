package com.example.playlistmaker.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.domain.entity.Track
import java.text.SimpleDateFormat
import java.util.Locale

class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var trackImageView: ImageView
    private var trackArtistName: TextView
    private var trackName: TextView
    private var trackTime: TextView
    private val dateFormat by lazy { SimpleDateFormat("mm:ss", Locale.getDefault()) }
    private val corner by lazy { itemView.resources.getDimension(R.dimen.corner_radius).toInt() }

    init {
        trackImageView = itemView.findViewById(R.id.track_image)
        trackArtistName = itemView.findViewById(R.id.author_name)
        trackName = itemView.findViewById(R.id.track_name)
        trackTime = itemView.findViewById(R.id.time)
    }

    fun bind(track: Track) {
        trackName.text = track.trackName
        trackArtistName.text = track.artistName
        if (track.trackTimeMillis != null){
            trackTime.text =
                dateFormat.format(track.trackTimeMillis.toInt())}
        else trackTime.text =
            trackTime.resources.getText(R.string.default_track_time)


        Glide
            .with(itemView)
            .load(track.artworkUrl100)
            .fitCenter()
            .placeholder(R.drawable.placeholder)
            .transform(RoundedCorners(corner))
            .into(trackImageView)

    }


}
