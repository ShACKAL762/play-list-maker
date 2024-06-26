package com.example.playlistmaker.ui.player.view_model

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.domain.entity.Track
import com.example.playlistmaker.domain.player.interactors.MediaPlayerInteractor
import com.example.playlistmaker.domain.player.interactors.TrackListInteractor
import com.example.playlistmaker.domain.player.state.PlayerState
import java.text.SimpleDateFormat
import java.util.Locale

class PlayerViewModel(
    private val playerInteractor: MediaPlayerInteractor,
    private val playerTrackInteractor: TrackListInteractor
) : ViewModel() {

    companion object {
        private const val TIMER_DELAY_MILLS = 300L
    }

    private val dateFormat by lazy { SimpleDateFormat("mm:ss", Locale.getDefault()) }


    private val currentTime = MutableLiveData<String>()
    private val buttonStatePlay = MutableLiveData<Boolean>()
    private val trackData = MutableLiveData<Track>()

    val currentTimeLiveData: LiveData<String> = currentTime
    val buttonStateLiveData: LiveData<Boolean> = buttonStatePlay
    val trackLiveData: LiveData<Track> = trackData

    private val handler = Handler(Looper.getMainLooper())

    init {
        trackData.value = playerTrackInteractor.getTrack()
        preparePlayer(trackData.value!!)
    }

    private val time = Runnable {
        currentTime.value = playerCurrentTime()
        timerStart()
    }

    private fun preparePlayer(track: Track) {
        playerInteractor.playerPrepare(track.previewUrl)
        renderPlayerState()
        handler.removeCallbacks(time)

    }

    private fun renderPlayerState() {
        when (playerInteractor.playerState()) {
            PlayerState.PLAY -> {
                buttonStatePlay.value = true
                timerStart()
            }

            PlayerState.PAUSE -> buttonStatePlay.value = false
            PlayerState.DEFAULT -> buttonStatePlay.value = false
            PlayerState.RELEASE -> Unit
            PlayerState.PREPARED -> {
                buttonStatePlay.value = false
                currentTime.value = dateFormat.format(0)
            }
        }
    }

    private fun timerStart() {
        if (playerInteractor.playerState() == PlayerState.PREPARED)
            renderPlayerState()
        handler.postDelayed(time, TIMER_DELAY_MILLS)
    }

    private fun playerCurrentTime(): String {
        return dateFormat.format(playerInteractor.currentMills())

    }

    override fun onCleared() {
        handler.removeCallbacks(time)
        playerInteractor.release()
        super.onCleared()
    }

    fun pause() {
        playerInteractor.pause()
        handler.removeCallbacks(time)
    }

    fun control() {
        playerInteractor.playerControl()
        renderPlayerState()
    }
}