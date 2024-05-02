package com.example.playlistmaker.data

import android.content.Context
import com.example.playlistmaker.domain.entity.History
import com.example.playlistmaker.domain.entity.Track
import com.google.gson.Gson

class SearchHistory {
    private val historyMaxSize = 10
    fun getHistoryList(context: Context): MutableList<Track> {
        val history = context.getSharedPreferences(historyPref, Context.MODE_PRIVATE)
            .getString(historyPref, "")
        var historyList: MutableList<Track> = mutableListOf()
        if (!history.equals("")) {
            historyList = Gson().fromJson(history, History::class.java).list
        }
        return historyList
    }

    fun setHistory(context: Context, itemView: Track) {
        val historyList = getHistoryList(context)
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
    companion object{
        private const val historyPref = "history"
    }
}