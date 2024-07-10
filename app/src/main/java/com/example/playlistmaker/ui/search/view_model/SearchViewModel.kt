package com.example.playlistmaker.ui.search.view_model

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.data.search.state.SearchState
import com.example.playlistmaker.domain.entity.Resource
import com.example.playlistmaker.domain.entity.Track
import com.example.playlistmaker.domain.entity.TrackList
import com.example.playlistmaker.domain.search.Interactor.HistoryTrackListInteractor
import com.example.playlistmaker.domain.search.Interactor.SearchActivityStateInteractor
import com.example.playlistmaker.domain.search.Interactor.SearchTrackListInteractor
import com.example.playlistmaker.ui.player.activity.PlayerActivity
import com.example.playlistmaker.ui.search.view_model.recycleView.SearchRecycleAdapter
import com.example.playlistmaker.ui.search.view_model.view_state.SearchViewState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val historyTrackListInteractor: HistoryTrackListInteractor,
    private val stateInteractor: SearchActivityStateInteractor,
    private val searchTrackListInteractor: SearchTrackListInteractor
) : ViewModel() {
    companion object {
        private const val SEARCH_DELAY = 2000L
        private const val ITUNES_URL = "https://itunes.apple.com"
        private const val CLICK_DEBOUNCE_DELAY = 300L

    }
    private var isClickAllowed = true
    private var searchJob: Job? = null


    //Изменяемые переменные
    private val trackList = MutableLiveData<List<Track>>()
    private val searchState = MutableLiveData<SearchState>()

    //Переменные для Observer
    val trackListLiveData: LiveData<List<Track>> = trackList
    val searchStateLiveData: LiveData<SearchState> = searchState

    init {
        searchState.value = stateInteractor.changeState(SViewState.DEFAULT)
    }

    private fun changeStateAsyn(state: SViewState) {
        searchState.postValue(stateInteractor.changeState(state))
    }

    private fun render(searchViewState: SearchViewState) {
        when (searchViewState) {
            is SearchViewState.Loading -> {
                changeStateAsyn(SViewState.LOADING)
                searchState.postValue(stateInteractor.changeState(SViewState.LOADING))
            }

            is SearchViewState.Content -> {
                trackList.postValue(searchViewState.tracks)
                changeStateAsyn(SViewState.SUCCESS)
            }

            is SearchViewState.Empty -> {
                changeStateAsyn(SViewState.NOT_FOUND)
            }

            is SearchViewState.LostConnection -> {
                changeStateAsyn(SViewState.LOST_CONNECTION)
            }

            is SearchViewState.ShowHistory -> {
                changeStateAsyn(SViewState.SHOW_HISTORY)
                trackList.value = searchViewState.tracks
            }

            is SearchViewState.HideHistory -> {
                changeStateAsyn(SViewState.HIDE_HISTORY)
            }
        }
    }

    fun cleanHistory() {
        render(SearchViewState.HideHistory)
        trackList.value = mutableListOf()
        historyTrackListInteractor.clearHistory()
    }

    fun setHistoryVisibleState() {
        if (historyTrackListInteractor.getTrackList().isNotEmpty()) {
            render(SearchViewState.ShowHistory(historyTrackListInteractor.getTrackList()))
        } else
            render(SearchViewState.HideHistory)
    }

    fun setTrack(track: Track) {
        historyTrackListInteractor.setTrack(track)
    }

    fun writeEnd(searchRequest: String) {
        searchJob?.cancel()
        if (searchRequest.isNotEmpty()) {
            render(SearchViewState.Loading)
            searchJob = viewModelScope.launch {
                delay(SEARCH_DELAY)
                search(searchRequest).collect { response ->

                    when (response) {
                        is Resource.Success -> {
                            val trackListResponse = response.data.results
                            if (trackListResponse.isEmpty()) {
                                render(SearchViewState.Empty)
                            } else {
                                render(SearchViewState.Content(trackListResponse))
                            }
                        }

                        is Resource.Fail -> {
                            changeStateAsyn(SViewState.LOST_CONNECTION)
                        }
                    }
                }
            }
        }
    }


    private fun search(searchRequest: String): Flow<Resource<TrackList>> {

        return searchTrackListInteractor
            .getTrackListResponse(ITUNES_URL, searchRequest)

    }

    override fun onCleared() {
        searchJob?.cancel()
        super.onCleared()
    }

    fun getRecycleAdapter(tracks: MutableList<Track>, context: Context): SearchRecycleAdapter =
        SearchRecycleAdapter(tracks) {
            viewModelScope.launch {
                if (isClickAllowed) {
                    isClickAllowed = false
                    val intent = Intent(context, PlayerActivity::class.java)
                    setTrack(it)
                    context.startActivity(intent)
                    delay(CLICK_DEBOUNCE_DELAY)
                    isClickAllowed = true
                }
            }
        }



    enum class SViewState {
        SUCCESS,
        LOADING,
        NOT_FOUND,
        LOST_CONNECTION,
        SHOW_HISTORY,
        HIDE_HISTORY,
        DEFAULT
    }
}


