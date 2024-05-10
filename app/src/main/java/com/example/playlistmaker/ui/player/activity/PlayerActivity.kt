package com.example.playlistmaker.ui.player.activity


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker.Creator
import com.example.playlistmaker.R
import com.example.playlistmaker.domain.entity.Track
import com.example.playlistmaker.domain.model.PlayerState
import com.example.playlistmaker.ui.player.PlayerAdapter
import java.text.SimpleDateFormat
import java.util.Locale

class PlayerActivity : AppCompatActivity() {

    companion object {
        private const val TIMER_DELAY_MILLS = 300L
    }

    private var mediaPlayer = Creator.provideMediaPlayerInteract()
    private var trackGetter = Creator.provideTrackListInteractor()

    private lateinit var playButton: ImageButton
    private lateinit var backButton: ImageButton
    private lateinit var playTime: TextView
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
        playButton = findViewById(R.id.play)
        backButton = findViewById(R.id.arrow_back)

        val trackData = trackGetter.getTrack(this)
        val playerAdapter = PlayerAdapter(this)

        playerAdapter.onBindPlayerHolder(trackData)

        playButton.isEnabled = false

        preparePlayer(trackData)

        playButton.setOnClickListener {
            mediaPlayer.playerControl()
            playButtonState()
        }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                stopActivity()
            }
        })


        backButton.setOnClickListener {
            stopActivity()
        }
    }

    private fun stopActivity() {
        handler.removeCallbacks(time)
        finish()
    }

    private fun preparePlayer(track: Track) {
        mediaPlayer.playerPrepare(track.previewUrl)
        playButton.isEnabled = true
        playButton.setImageResource(R.drawable.play_button)
        handler.removeCallbacks(time)
        playTime.text = dateFormat.format(0)
    }


    private fun playButtonState() {
        when (mediaPlayer.playerState()) {
            PlayerState.PLAY -> {
                playButton.setImageResource(R.drawable.pause_button)
                timerStart()
            }
            PlayerState.PAUSE -> playButton.setImageResource(R.drawable.play_button)
            PlayerState.DEFAULT -> playButton.setImageResource(R.drawable.play_button)
            PlayerState.RELEASE -> {}
            PlayerState.PREPARED -> playButton.setImageResource(R.drawable.play_button)

        }
    }

    private fun timerStart() {
        handler.postDelayed(time, TIMER_DELAY_MILLS)
    }

    private fun currentTime(): String? {
        return dateFormat.format(mediaPlayer.currentMills())
    }

    override fun onPause() {
        mediaPlayer.pause()
        handler.removeCallbacks(time)
        super.onPause()
    }

    override fun onDestroy() {
        mediaPlayer.release()
        handler.removeCallbacks(time)
        super.onDestroy()
    }
}



