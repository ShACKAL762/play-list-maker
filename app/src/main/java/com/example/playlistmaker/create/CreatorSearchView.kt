package com.example.playlistmaker.create

import android.content.Context
import com.example.playlistmaker.data.search.HistoryTrackListRepositoryImpl
import com.example.playlistmaker.data.search.SearchActivityStateRepositoryImpl
import com.example.playlistmaker.domain.search.Interactor.HistoryTrackListInteractor
import com.example.playlistmaker.domain.search.Interactor.SearchActivityStateInteractor
import com.example.playlistmaker.domain.search.InteractorImpl.HistoryTrackListInteractorImpl
import com.example.playlistmaker.domain.search.InteractorImpl.SearchActivityStateInteractorImpl
import com.example.playlistmaker.domain.search.repository.HistoryTrackListRepository
import com.example.playlistmaker.domain.search.repository.SearchActivityStateRepository

object CreatorSearchView {
    /***
    Create searchActivityStateInteractor
     */
    fun provideSearchActivityStateInteractor(): SearchActivityStateInteractor {
        return SearchActivityStateInteractorImpl(provideSearchActivityState())
    }

    private fun provideSearchActivityState(): SearchActivityStateRepository {
        return SearchActivityStateRepositoryImpl()
    }
    /***
    Create HistoryTrackListInteractor
     */
    fun provideHistoryTrackListInteractor(context: Context): HistoryTrackListInteractor{
        return HistoryTrackListInteractorImpl(provideHistoryTrackList(context))
    }

    private fun provideHistoryTrackList(context: Context): HistoryTrackListRepository {
        return HistoryTrackListRepositoryImpl(context)
    }


}