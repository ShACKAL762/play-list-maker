package com.example.playlistmaker.ui.library.activity.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.domain.entity.Track
import com.example.playlistmaker.domain.library.interactors.FavoriteListInteractor
import kotlinx.coroutines.launch

class FavoriteListFragmentViewModel(
    private val favoriteListInteractor: FavoriteListInteractor
) : ViewModel() {


    //Изменяемые переменные
    private val trackList = MutableLiveData<List<Track>>()
    private val searchState = MutableLiveData<Boolean>()

    //Переменные для Observer
    val trackListLiveData: LiveData<List<Track>> = trackList
    val libraryStateLiveData: LiveData<Boolean> = searchState


    fun updateList() {
        viewModelScope.launch {
            favoriteListInteractor.getFavoriteList().collect {
                trackList.value = it.asReversed()
            }
            searchState.value = !trackList.value.isNullOrEmpty()
        }
    }
}