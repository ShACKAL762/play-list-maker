package com.example.playlistmaker.ui

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker.R
import com.example.playlistmaker.presentation.Player
import com.example.playlistmaker.presentation.PlayerAdapter
import com.example.playlistmaker.data.PlayerManager
import com.example.playlistmaker.data.SearchHistory
import com.example.playlistmaker.presentation.models.Track
import java.text.SimpleDateFormat
import java.util.Locale

class PlayerActivity : AppCompatActivity() {

    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
        private const val TIMER_DELAY_MILLS = 300L
    }

    private lateinit var mediaPlayer : MediaPlayer
    private lateinit var player: Player

    private lateinit var playButton: ImageButton
    private lateinit var playTime: TextView
    private var playerState = STATE_DEFAULT
    private val handler = Handler(Looper.getMainLooper())
    private val time = Runnable {
        playTime.text = currentTime()
        timerStart()
    }
    private val dateFormat by lazy { SimpleDateFormat("mm:ss", Locale.getDefault()) }

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
        player.pause()
    }

    private fun mediaPlay() {
        playButton.setImageResource(R.drawable.pause_button)
        timerStart()
        player.play()
    }

    private fun stopActivity() {

        handler.removeCallbacks(time)
        finish()
    }


    private fun preparePlayer(track: Track) {
        player = PlayerManager()
        mediaPlayer = player.playerPrepare(track.previewUrl)


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
            playTime.text = dateFormat.format(0)
        }
    }

    private fun timerStart() {
        handler.postDelayed(time, TIMER_DELAY_MILLS)

    }

    private fun currentTime(): String? {
        return dateFormat.format(mediaPlayer.currentPosition)
    }

    override fun onPause() {
        mediaPause()
        handler.removeCallbacks(time)
        super.onPause()
    }

    override fun onDestroy() {
        player.release()
        handler.removeCallbacks(time)
        super.onDestroy()
    }
}



