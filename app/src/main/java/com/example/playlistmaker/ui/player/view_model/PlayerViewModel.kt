package com.example.playlistmaker.ui.player.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.domain.entity.Track
import com.example.playlistmaker.domain.player.interactors.MediaPlayerInteractor
import com.example.playlistmaker.domain.player.interactors.TrackListInteractor
import com.example.playlistmaker.domain.player.state.PlayerState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

    private var timerJob: Job? = null


    init {
        trackData.value = playerTrackInteractor.getTrack()
        preparePlayer(trackData.value!!)
    }


    private fun preparePlayer(track: Track) {
        playerInteractor.playerPrepare(track.previewUrl)
        renderPlayerState()
        timerJob?.cancel()

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

        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (playerInteractor.playerState() == PlayerState.PLAY) {
                delay(TIMER_DELAY_MILLS)
                currentTime.value = playerCurrentTime()
                if (playerInteractor.playerState() == PlayerState.PREPARED){
                    renderPlayerState()
                }
            }
        }
    }

    private fun playerCurrentTime(): String {
        return dateFormat.format(playerInteractor.currentMills())

    }

    override fun onCleared() {
        timerJob?.cancel()
        playerInteractor.release()
        super.onCleared()
    }

    fun pause() {
        playerInteractor.pause()
        timerJob?.cancel()
        renderPlayerState()
    }

    fun control() {
        playerInteractor.playerControl()
        renderPlayerState()
    }
}