package com.example.playlistmaker.ui.playlist.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.domain.entity.Album
import com.example.playlistmaker.domain.entity.Track
import com.example.playlistmaker.domain.library.interactors.AlbumListInteractor
import com.example.playlistmaker.domain.player.interactors.TrackListInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlayListViewModel(
    private val albumListInteractor: AlbumListInteractor,
    private val trackListInteractor: TrackListInteractor

) : ViewModel() {


    private val albumMutableLiveData = MutableLiveData<Album>()
    private val trackListMutableLiveData = MutableLiveData<List<Track>>()

    val albumLiveData: LiveData<Album> = albumMutableLiveData
    val trackListLiveData: LiveData<List<Track>> = trackListMutableLiveData


    fun updateTrackList(albumId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            albumListInteractor.getAlbumTrackList(albumId).collect() {
                trackListMutableLiveData.postValue(it)
            }
        }
    }


    fun updateAlbumState(albumId: Int) {
        updateTrackList(albumId)
        viewModelScope.launch(Dispatchers.IO) {
            albumListInteractor.getAlbum(albumId).collect {
                albumMutableLiveData.postValue(it)
            }
        }
    }

    fun deleteTrack(track: Track) {
        viewModelScope.launch(Dispatchers.IO) {
            albumLiveData.value?.id?.let { albumListInteractor.deleteTrack(track, it) }
            albumLiveData.value?.id?.let { updateAlbumState(it) }
        }
    }

    fun share() {
        viewModelScope.launch {
            albumListInteractor.share(albumLiveData.value?.id)
        }
    }

    fun deleteAlbum(album: Album) {
        viewModelScope.launch(Dispatchers.IO) {
            albumListInteractor.deleteAlbum(album)
        }
    }
}


