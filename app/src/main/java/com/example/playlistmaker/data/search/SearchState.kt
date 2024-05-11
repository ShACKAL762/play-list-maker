package com.example.playlistmaker.data.search

enum class SearchState(
    val recycleView: Boolean,
    val lostConnection: Boolean,
    val notFound:Boolean,
    val progressBar: Boolean,
    val cleanHistoryButton: Boolean,
    val historyMessage: Boolean,
    ) {

    NOT_FOUND(
        recycleView = false,
        lostConnection = false,
        notFound = true,
        progressBar = false,
        cleanHistoryButton = false,
        historyMessage = false),

    LOST_CONNECTION(
        recycleView = false,
        lostConnection = true,
        notFound = false,
        progressBar = false,
        cleanHistoryButton = false,
        historyMessage = false),

    SUCCESS(
        recycleView = true,
        lostConnection = false,
        notFound = false,
        progressBar = false,
        cleanHistoryButton = false,
        historyMessage = false),

    LOADING(
        recycleView = false,
        lostConnection = false,
        notFound = false,
        progressBar = true,
        cleanHistoryButton = false,
        historyMessage = false),

    SHOW_HISTORY(
        recycleView = true,
        lostConnection = false,
        notFound = false,
        progressBar = false,
        cleanHistoryButton = true,
        historyMessage = true),

    HIDE_HISTORY(
        recycleView = true,
        lostConnection = false,
        notFound = false,
        progressBar = false,
        cleanHistoryButton = false,
        historyMessage = false),

}