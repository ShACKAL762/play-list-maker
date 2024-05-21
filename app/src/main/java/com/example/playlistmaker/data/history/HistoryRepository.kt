package com.example.playlistmaker.data.history

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker.data.entity.History
import com.example.playlistmaker.data.entity.Track
import com.example.playlistmaker.domain.player.repositories.TrackListRepository
import com.google.gson.Gson

class HistoryRepository (val context: Context) : TrackListRepository {
    private val historyMaxSize = 10

    override fun getTrackList(): List<Track> {
        return getHistoryList()
    }

    override fun getTrack(): Track {
        return getHistoryList().first()
    }

    fun getHistoryList(): MutableList<Track> {
        val history = context.getSharedPreferences(historyPref, Context.MODE_PRIVATE)
            .getString(historyPref, "")
        var historyList: MutableList<Track> = mutableListOf()
        if (!history.equals("")) {
            historyList = Gson().fromJson(history, History::class.java).list
        }
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
            .putString(historyPref, Gson().toJson(History(historyList), History::class.java)).apply()

    }

    fun clean() {
        context.getSharedPreferences("history", AppCompatActivity.MODE_PRIVATE).edit()
            .putString("history", Gson().toJson(History(mutableListOf()), History::class.java))
            .apply()
    }

    companion object{
        private const val historyPref = "history"
    }


}