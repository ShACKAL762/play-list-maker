package com.example.playlistmaker.ui.player

import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.domain.entity.Track
import com.example.playlistmaker.ui.player.activity.PlayerActivity
import java.text.SimpleDateFormat
import java.util.Locale

class PlayerHolder(private val itemView: PlayerActivity) {


    private var trackImageView: ImageView
    private var trackArtistName: TextView
    private var trackName: TextView
    private var trackTime: TextView
    private var trackAlbum: TextView
    private var trackYear: TextView
    private var trackStyle: TextView
    private var trackCountry: TextView
    private val timeFormat by lazy { SimpleDateFormat("mm:ss", Locale.getDefault()) }


    private val corner by lazy { itemView.resources.getDimension(R.dimen.big_corner_radius).toInt() }


    init {
        trackImageView = itemView.findViewById(R.id.track_image)
        trackArtistName = itemView.findViewById(R.id.track_artist_name)
        trackName = itemView.findViewById(R.id.track_name)
        trackTime = itemView.findViewById(R.id.track_time)
        trackAlbum = itemView.findViewById(R.id.album_count)
        trackYear = itemView.findViewById(R.id.year_count)
        trackStyle = itemView.findViewById(R.id.style_count)
        trackCountry = itemView.findViewById(R.id.country_count)

    }
    fun bind(track: Track) {
        trackName.text = track.trackName
        trackArtistName.text = track.artistName
        trackAlbum.text = track.collectionName
        trackYear.text = track.releaseDate.removeRange(4,track.releaseDate.length)
        trackStyle.text = track.primaryGenreName
        trackCountry.text = track.country
        if (track.trackTimeMillis != null)
            trackTime.text =
                timeFormat.format(track.trackTimeMillis.toInt())
        else trackTime.text =
            trackTime.resources.getText(R.string.default_track_time)


        Glide
            .with(itemView)
            .load(getCoverArtwork(track.artworkUrl100))
            .centerCrop()
            .placeholder(R.drawable.placeholder)
            .transform(RoundedCorners(corner))
            .into(trackImageView)

    }
    private fun getCoverArtwork(artworkUrl100:String) = artworkUrl100.replaceAfterLast('/',"512x512bb.jpg")
}