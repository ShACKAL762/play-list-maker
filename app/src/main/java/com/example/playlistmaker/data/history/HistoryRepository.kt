package com.example.playlistmaker.data.history

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker.data.entity.History
import com.example.playlistmaker.domain.entity.Track
import com.example.playlistmaker.domain.player.repositories.TrackListRepository
import com.google.gson.Gson

class HistoryRepository (private val context: Context, private val gson: Gson) : TrackListRepository {
    private val historyMaxSize = 10

    override fun getTrackList(): List<Track> {
        return getHistoryList()
    }

    override fun getTrack(): Track? {
        if (getHistoryList().isEmpty())
            return null
        return getHistoryList().first()
    }

    fun getHistoryList(): MutableList<Track> {
        val history = context.getSharedPreferences(historyPref, Context.MODE_PRIVATE)
            .getString(historyPref, "")
        var historyList: MutableList<Track> = mutableListOf()
        if (!history.equals("")) {
            historyList = gson.fromJson(history, History::class.java).list
        }
        if (history.isNullOrEmpty())
            return mutableListOf()
        return historyList
    }

    fun setHistory(itemView: Track) {
        val historyList = getHistoryList()
        val iterator = historyList.iterator()

        while (iterator.hasNext()) {
            val iterable = iterator.next()
            if (iterable.trackId == itemView.trackId) {
                iterator.remove()
                break
            }
        }

        historyList.add(0, itemView)
        while (historyList.size > historyMaxSize)
            historyList.removeLast()

        context.getSharedPreferences(historyPref, Context.MODE_PRIVATE).edit()
            .putString(historyPref, gson.toJson(History(historyList), History::class.java)).apply()

    }

    fun clean() {
        context.getSharedPreferences("history", AppCompatActivity.MODE_PRIVATE).edit()
            .putString("history", gson.toJson(History(mutableListOf()), History::class.java))
            .apply()
    }

    companion object{
        private const val historyPref = "history"
    }


}