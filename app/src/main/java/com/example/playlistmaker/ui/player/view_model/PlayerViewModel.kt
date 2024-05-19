package com.example.playlistmaker.ui.player.view_model

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.domain.entity.Track
import com.example.playlistmaker.domain.model.PlayerState
import com.example.playlistmaker.domain.player.interactors.MediaPlayerInteractor
import com.example.playlistmaker.domain.player.interactors.TrackListInteractor
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
    private val buttonState = MutableLiveData<Boolean>()
    private val trackData = MutableLiveData<Track>()

    val currentTimeLiveData:LiveData<String> = currentTime
    val buttonStateLiveData:LiveData<Boolean> = buttonState
    val trackLiveData:LiveData<Track> = trackData

    private val handler = Handler(Looper.getMainLooper())

    private val time = Runnable {
        currentTime.value = playerCurrentTime()
        timerStart()
    }
    init {
        trackData.value = playerTrackInteractor.getTrack()
        preparePlayer(trackData.value!!)
    }

    private fun preparePlayer(track: Track){
        playerInteractor.playerPrepare(track.previewUrl)
        renderPlayerState()
        handler.removeCallbacks(time)

    }

    private fun renderPlayerState() {
        when (playerInteractor.playerState()) {
            PlayerState.PLAY -> {
                buttonState.value = true
                timerStart()
            }

            PlayerState.PAUSE -> buttonState.value = false
            PlayerState.DEFAULT -> buttonState.value = false
            PlayerState.RELEASE -> Unit
            PlayerState.PREPARED ->{
                buttonState.value = false
                currentTime.value = dateFormat.format(0)}
        }
    }

    private fun timerStart() {
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