package com.example.playlistmaker.ui.search.view_model

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.data.search.state.SearchState
import com.example.playlistmaker.domain.entity.Track
import com.example.playlistmaker.domain.search.Interactor.HistoryTrackListInteractor
import com.example.playlistmaker.domain.search.Interactor.SearchActivityStateInteractor
import com.example.playlistmaker.domain.search.Interactor.SearchTrackListInteractor
import com.example.playlistmaker.ui.models.TrackList
import com.example.playlistmaker.ui.search.view_model.view_state.SearchViewState
import retrofit2.Response

class SearchViewModel(
    private val historyTrackListInteractor: HistoryTrackListInteractor,
    private val stateInteractor: SearchActivityStateInteractor,
    private val searchTrackListInteractor: SearchTrackListInteractor
) : ViewModel() {
    companion object {
        private const val SEARCH_DELAY = 2000L
        private const val ITUNES_URL = "https://itunes.apple.com"
    }

    private val handler = Handler(Looper.getMainLooper())
    private var searchEvent = Runnable {}

    private lateinit var response: Response<TrackList>

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

    fun writeEnd(searchRequest : String) {
        render(SearchViewState.Loading)
        handler.removeCallbacks(searchEvent)
        if (searchRequest.isNotEmpty()) {
            searchEvent = Runnable { search(searchRequest) }
            handler.postDelayed(searchEvent, SEARCH_DELAY)
        }
    }

    private fun search(searchRequest: String) {
        Thread {
            response = searchTrackListInteractor
                .getTrackListResponse(ITUNES_URL, searchRequest)
            if (response.code() == 200) {
                val trackListResponse = response.body()?.results
                if (trackListResponse.isNullOrEmpty()) {
                    render(SearchViewState.Empty)
                } else {
                    render(SearchViewState.Content(trackListResponse))
                }
            } else changeStateAsyn(SViewState.LOST_CONNECTION)
        }
            .start()
    }
    override fun onCleared() {
        handler.removeCallbacks(searchEvent)
        super.onCleared()
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