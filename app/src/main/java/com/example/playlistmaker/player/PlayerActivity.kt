package com.example.playlistmaker.player

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker.R
import com.example.playlistmaker.search.SearchHistory
import com.example.playlistmaker.search.Track
import java.text.SimpleDateFormat
import java.util.Locale

class PlayerActivity : AppCompatActivity() {

    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
    }

    private val mediaPlayer = MediaPlayer()

    private lateinit var playButton: ImageButton
    private lateinit var playTime: TextView
    private var playerState = STATE_DEFAULT
    private val handler = Handler(Looper.getMainLooper())
    private val time = Runnable {
        playTime.text = currentTime()

        timerStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        playTime = findViewById(R.id.play_time)

        val history = SearchHistory()
        val playerAdapter = PlayerAdapter(this)
        val list = history.getHistoryList(this)
        playerAdapter.onBindPlayerHolder(list)

        playButton = findViewById(R.id.play)
        playButton.isEnabled = false

        preparePlayer(list.first())

        playButton.setOnClickListener {

            if (mediaPlayer.isPlaying) {
                mediaPause()
            } else {
                mediaPlay()
            }
        }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                stopActivity()
            }
        })

        val backButton = findViewById<ImageButton>(R.id.arrow_back)
        backButton.setOnClickListener {
            stopActivity()
        }
    }

    private fun mediaPause() {
        playButton.setImageResource(R.drawable.play_button)
        mediaPlayer.pause()
    }

    private fun mediaPlay() {
        playButton.setImageResource(R.drawable.pause_button)
        timerStart()
        mediaPlayer.start()
    }

    private fun stopActivity() {

        handler.removeCallbacks(time)
        finish()
    }


    private fun preparePlayer(track: Track) {
        mediaPlayer.setDataSource(track.previewUrl)
        mediaPlayer.prepareAsync()

        mediaPlayer.setOnPreparedListener {
            playButton.isEnabled = true
            playButton.setImageResource(R.drawable.play_button)
            playerState = STATE_PREPARED
        }
        mediaPlayer.setOnCompletionListener {
            playButton.isEnabled = true
            playButton.setImageResource(R.drawable.play_button)
            playerState = STATE_PREPARED

            handler.removeCallbacks(time)
            playTime.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(0)
        }
    }

    private fun timerStart() {

        handler.postDelayed(time, 300)

    }

    private fun currentTime(): String? {
        return SimpleDateFormat("mm:ss", Locale.getDefault()).format(mediaPlayer.currentPosition)
    }

    override fun onPause() {
        mediaPause()
        super.onPause()
    }

    override fun onDestroy() {
        mediaPlayer.release()

        super.onDestroy()
    }
}



