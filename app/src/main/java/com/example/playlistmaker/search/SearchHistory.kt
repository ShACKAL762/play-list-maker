package com.example.playlistmaker.search

import android.content.Context
import android.widget.Toast
import com.google.gson.Gson

class SearchHistory {
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
        while (historyList.size > 10)
            historyList.removeLast()


        context.getSharedPreferences(historyPref, Context.MODE_PRIVATE).edit()
            .putString(historyPref, Gson().toJson(History(historyList), History::class.java)).apply()

    }
    companion object{
        private const val historyPref = "history"
    }
}