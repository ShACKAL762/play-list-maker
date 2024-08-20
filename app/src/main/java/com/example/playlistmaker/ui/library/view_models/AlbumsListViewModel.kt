package com.example.playlistmaker.ui.library.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.domain.entity.Album
import com.example.playlistmaker.domain.library.interactors.AlbumListInteractor
import kotlinx.coroutines.launch

class AlbumsListViewModel(
    val albumListInteractor: AlbumListInteractor
) : ViewModel() {
    private val albumList = MutableLiveData<List<Album>>()
    private val albumListViewState = MutableLiveData<Boolean>()

    val albumListLiveData:LiveData<List<Album>> = albumList
    val albumListViewStateLiveData:LiveData<Boolean> = albumListViewState
    fun updateAlbumList() {
        viewModelScope.launch {
            albumListInteractor.getAlbumList().collect {
                albumList.value = it.reversed()
            }
        albumListViewState.value = !albumList.value.isNullOrEmpty()
        }
    }

}