package com.example.playlistmaker.ui.library.view_models

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.domain.entity.Album
import com.example.playlistmaker.domain.library.interactors.AlbumListInteractor
import kotlinx.coroutines.launch

class CreateAlbumViewModel(
    private val albumListInteractor: AlbumListInteractor
) : ViewModel() {
    private val createAlbumLiveData = MutableLiveData<CreateAlbumState>()
    private val album = MutableLiveData<Album>()
    val albumLiveData: LiveData<Album> = album

    init {
        createAlbumLiveData.value = CreateAlbumState()
    }

    fun createAlbum() {
        viewModelScope.launch {
            albumListInteractor.insertAlbum(
                Album(
                    null,
                    createAlbumLiveData.value?.albumName as String,
                    createAlbumLiveData.value?.about as String,
                    0,
                    createAlbumLiveData.value?.imageSrc.toString(),
                    0
                )
            )
        }
    }

    fun uriCash(uri: Uri) {
        createAlbumLiveData.value?.imageSrc = uri
    }

    fun setAlbumName(s: CharSequence?) {
        createAlbumLiveData.value?.albumName = s.toString()

    }

    fun setAlbumAbout(s: CharSequence?) {
        createAlbumLiveData.value?.about = s.toString()

    }

    fun dialog() {

    }

    fun isEmpty(): Boolean {
        return if (createAlbumLiveData.value?.albumName?.isEmpty() == false)
            false
        else if (createAlbumLiveData.value?.about?.isEmpty() == false)
            false
        else if (createAlbumLiveData.value?.imageSrc.toString().isEmpty() == false)
            false
        else true


    }

    fun getAlbum(albumId: Int) {
        viewModelScope.launch {
            albumListInteractor.getAlbum(albumId).collect() {
                album.postValue(it)
            }

        }
    }

    fun updateAlbum(album: Album) {
        viewModelScope.launch {
            albumListInteractor.updateAlbum(
                Album(
                    album.id,
                    createAlbumLiveData.value?.albumName as String,
                    createAlbumLiveData.value?.about as String,
                    album.tracksQuantity,
                    createAlbumLiveData.value?.imageSrc.toString(),
                    album.time
                )
            )
        }
    }
}
