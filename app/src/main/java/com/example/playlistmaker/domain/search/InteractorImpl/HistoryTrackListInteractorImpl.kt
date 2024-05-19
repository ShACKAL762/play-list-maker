package com.example.playlistmaker.domain.search.InteractorImpl

import com.example.playlistmaker.domain.entity.Track
import com.example.playlistmaker.domain.search.Interactor.HistoryTrackListInteractor
import com.example.playlistmaker.domain.search.repository.HistoryTrackListRepository

class HistoryTrackListInteractorImpl(private val historyTrackListRepository: HistoryTrackListRepository) :
    HistoryTrackListInteractor {
    override fun getTrackList(): List<Track> {
        return historyTrackListRepository.getTrackList()
    }

    override fun getTrack(): Track {
        return historyTrackListRepository.getTrack()
    }

    override fun setTrack(track: Track) {
        historyTrackListRepository.setTrack(track)
    }

    override fun clearHistory() {
        historyTrackListRepository.cleanHistory()
    }
}