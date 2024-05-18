package com.example.playlistmaker.ui.search.view_model

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.data.search.SearchState
import com.example.playlistmaker.domain.entity.Track
import com.example.playlistmaker.domain.search.Interactor.HistoryTrackListInteractor
import com.example.playlistmaker.domain.search.Interactor.SearchActivityStateInteractor
import com.example.playlistmaker.domain.search.Interactor.SearchTrackListInteractor
import com.example.playlistmaker.ui.models.TrackList
import com.example.playlistmaker.ui.search.view_model.view_state.SearchViewState
import retrofit2.Response

class SearchViewModel(
    //private val searchLineInetractor:SearchLineInteractor,
    //private val searchRecycleInteractor: SearchRecycleInteractor,
    private val historyTrackListInteractor: HistoryTrackListInteractor,
    private val stateInteractor: SearchActivityStateInteractor,
    private val searchTrackListInteractor: SearchTrackListInteractor
) : ViewModel() {
    companion object {
        private const val SEARCH_DELAY = 2000L
        private const val ITUNES_URL = "https://itunes.apple.com"
    }

    private val handler = Handler(Looper.getMainLooper())
    private val searchEvent = Runnable { search() }

    private lateinit var response: Response<TrackList>


    //Изменяемые переменные
    private val trackList = MutableLiveData<List<Track>>()//todo
    private val searchState = MutableLiveData<SearchState>()
    private val searchLine = MutableLiveData<String>()

    //Переменные для Observer
    val trackListLiveData: LiveData<List<Track>> = trackList
    val searchLineLiveData: LiveData<String> = searchLine
    val searchStateLiveData: LiveData<SearchState> = searchState


    init {
        searchState.value = stateInteractor.changeState(SViewState.DEFAULT)
        searchLine.value = ""
    }


    fun setSearchLineData(text: String) {
        searchLine.value = text
    }

    fun changeStateAsyn(state: SViewState) {
        Log.e("ChangeState ", state.name)
        searchState.postValue(stateInteractor.changeState(state))
        println(searchState.value?.name)
    }

    fun render(searchViewState: SearchViewState) {
        handler.removeCallbacks(searchEvent)
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
                trackList.value = searchViewState.tracks
                changeStateAsyn(SViewState.SHOW_HISTORY)
            }

            SearchViewState.HideHistory ->
                changeStateAsyn(SViewState.HIDE_HISTORY)
        }
    }

    fun cleanHistory() {
        trackList.value = mutableListOf()
        historyTrackListInteractor.clearHistory()
        render(SearchViewState.HideHistory)
    }

    fun setHistoryVisibleState() {
        if (historyTrackListInteractor.getTrackList().isNotEmpty()) {
            render(SearchViewState.ShowHistory(historyTrackListInteractor.getTrackList()))
        } else
            render(SearchViewState.HideHistory)
    }

    fun writeEnd() {
        render(SearchViewState.Loading)
        handler.removeCallbacks(searchEvent)
        if (!searchLineLiveData.value.isNullOrEmpty()) {
            handler.postDelayed(searchEvent, SEARCH_DELAY)
        } else
            setHistoryVisibleState()
    }

    private fun search() {
        Thread {
            response = searchTrackListInteractor
                .getTrackListResponse(ITUNES_URL, searchLineLiveData.value!!)
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