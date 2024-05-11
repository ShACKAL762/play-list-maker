package com.example.playlistmaker.ui.search.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.data.search.SearchState
import com.example.playlistmaker.domain.search.Interactor.HistoryTrackListInteractor
import com.example.playlistmaker.domain.search.Interactor.SearchActivityStateInteractor

class SearchViewModel(
    //private val searchLineInetractor:SearchLineInteractor,
    //private val searchRecycleInteractor: SearchRecycleInteractor,
    private val historyTrackListInteractor: HistoryTrackListInteractor,
    private val stateInteractor: SearchActivityStateInteractor
) : ViewModel() {

    private val searchLine = MutableLiveData<String>()
    val searchLineLiveData: LiveData<String> = searchLine

    private val searchState = MutableLiveData<SearchState>()
    private val mutableNotFoundPlaceholder = MutableLiveData<Boolean>()
    private val mutableLostConnection = MutableLiveData<Boolean>()
    private val mutableRecycleView = MutableLiveData<Boolean>()
    private val mutableProgressBar = MutableLiveData<Boolean>()
    private val mutableCleanHistoryButton = MutableLiveData<Boolean>()
    private val mutableHistoryMessage = MutableLiveData<Boolean>()

    val notFoundPlaceholder: LiveData<Boolean> = mutableNotFoundPlaceholder
    val lostConnectionPlaceholder: LiveData<Boolean> = mutableLostConnection
    val recycleView: LiveData<Boolean> = mutableRecycleView
    val progressBar: LiveData<Boolean> = mutableProgressBar
    val cleanHistoryButton: LiveData<Boolean> = mutableCleanHistoryButton
    val historyMessage: LiveData<Boolean> = mutableHistoryMessage


    init {
        searchLine.value = ""
    }

    fun setSearchLineData(string:String) {
        searchLine.value = string
    }

    fun changeState(state: SViewState) {
        searchState.value = stateInteractor.changeState(state)
        updateStateLiveData()
    }

    private fun updateStateLiveData() {
        mutableRecycleView.value = searchState.value?.recycleView
        mutableLostConnection.value = searchState.value?.lostConnection
        mutableProgressBar.value = searchState.value?.progressBar
        mutableNotFoundPlaceholder.value = searchState.value?.notFound
        mutableCleanHistoryButton.value = searchState.value?.cleanHistoryButton
        mutableHistoryMessage.value = searchState.value?.historyMessage

    }
    fun cleanHistory(){
        historyTrackListInteractor.clearHistory()
    }
    fun setHistoryVisibleState() {
        if (historyTrackListInteractor.getTrackList().isNotEmpty()){
            changeState(SViewState.SHOW_HISTORY)
        }else
            changeState(SViewState.HIDE_HISTORY)
    }

    enum class SViewState {
        SUCCESS,
        LOAD,
        NOT_FOUND,
        LOST_CONNECTION,
        SHOW_HISTORY,
        HIDE_HISTORY

    }
}