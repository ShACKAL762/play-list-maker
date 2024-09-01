package com.example.playlistmaker.ui.player.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.domain.entity.Album
import com.example.playlistmaker.domain.entity.Track
import com.example.playlistmaker.domain.library.interactors.AlbumListInteractor
import com.example.playlistmaker.domain.library.interactors.FavoriteListInteractor
import com.example.playlistmaker.domain.player.interactors.MediaPlayerInteractor
import com.example.playlistmaker.domain.player.interactors.TrackListInteractor
import com.example.playlistmaker.domain.player.state.PlayerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class PlayerViewModel(
    private val favoriteListInteractor: FavoriteListInteractor,
    private val albumListInteractor: AlbumListInteractor,
    private val playerInteractor: MediaPlayerInteractor,
    private val playerTrackInteractor: TrackListInteractor,
) : ViewModel() {

    companion object {
        private const val TIMER_DELAY_MILLS = 300L

    }

    private val dateFormat by lazy { SimpleDateFormat("mm:ss", Locale.getDefault()) }

    private val currentTime = MutableLiveData<String>()
    private val likeStatePlay = MutableLiveData<Boolean>()
    private val buttonStatePlay = MutableLiveData<Boolean>()
    private val trackData = MutableLiveData<Track>()
    private val albumList = MutableLiveData<List<Album>>()
    private val insertStatus = MutableLiveData<InsertState>()


    val currentTimeLiveData: LiveData<String> = currentTime
    val likeStateLiveData: LiveData<Boolean> = likeStatePlay
    val buttonStateLiveData: LiveData<Boolean> = buttonStatePlay
    val trackLiveData: LiveData<Track> = trackData
    val albumListLiveData: LiveData<List<Album>> = albumList
    val insertStatusLiveData: LiveData<InsertState> = insertStatus

    private var timerJob: Job? = null
    private val dispatchersIO = Dispatchers.IO
    init {
        trackData.value = Track(
            "",
            "",
            "0",
            "",
            "",
            "2000",
            "",
            "",
            "",
            "",

        )
    }


    private fun preparePlayer(track: Track?) {
        if (track != null)
        if (track.previewUrl.isNotEmpty()){
            playerInteractor.playerPrepare(track.previewUrl)
            renderPlayerState()
            timerJob?.cancel()
        }

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
                if (playerInteractor.playerState() == PlayerState.PREPARED) {
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

    fun likeEvent() {
        viewModelScope.launch {
            if (likeStateLiveData.value == true) {
                likeStatePlay.value = false
                trackData.value?.isFavorite = false
                favoriteListInteractor.deleteTrack(trackLiveData.value as Track)
            } else {

                likeStatePlay.value = true
                trackData.value?.isFavorite = true
                favoriteListInteractor.insertTrack(trackLiveData.value as Track)
            }
        }

    }


    fun prepareTrack(trackId: String) {
        viewModelScope.launch {
            viewModelScope.launch(dispatchersIO) {
                albumListInteractor.getTrack(trackId).collect {
                    trackData.postValue(it)
                }
            }

            if (trackData.value?.trackId?.isEmpty() == true) {
                favoriteListInteractor.getFavoriteList().collect {
                    it.forEach { track ->
                        if (track.trackId == trackId)
                            trackData.value = track
                    }
                }
            }

            if (trackData.value?.trackId?.isEmpty() == true) {
                trackData.value = playerTrackInteractor.getTrack()
            }
            favoriteListInteractor.getFavoriteList().collect {
                it.forEach { favoriteTrack ->
                    if (trackData.value?.trackId == favoriteTrack.trackId) {
                        trackData.value?.isFavorite = true
                    }
                }
                likeStatePlay.value = trackData.value?.isFavorite
            }
        preparePlayer(trackData.value)
        }

    }

    fun insertTrack(it: Album) {
        viewModelScope.launch(dispatchersIO) {
            if (trackData.value != null && it.id != null) {
                val track = trackData.value as Track

                insertStatus.postValue(
                    InsertState(
                        albumListInteractor.addTrack(track, it.id) > 0,
                        it.name
                    )
                )

            }
            updateList()
        }

    }

    fun updateList() {
        viewModelScope.launch {
            albumListInteractor.getAlbumList().collect() {
                albumList.value = it
            }
        }
    }
}

class InsertState(var success: Boolean, var albumName: String)