package com.example.playlistmaker.data.search

enum class SearchState(
    val recycleView: Boolean,
    val lostConnection: Boolean,
    val notFound:Boolean,
    val progressBar: Boolean,
    val cleanHistoryButton: Boolean,
    val searchMessage: Boolean,
    ) {

    NOT_FOUND(
        recycleView = false,
        lostConnection = false,
        notFound = true,
        progressBar = false,
        cleanHistoryButton = false,
        searchMessage = false),

    LOST_CONNECTION(
        recycleView = false,
        lostConnection = true,
        notFound = false,
        progressBar = false,
        cleanHistoryButton = false,
        searchMessage = false),

    SUCCESS(
        recycleView = true,
        lostConnection = false,
        notFound = false,
        progressBar = false,
        cleanHistoryButton = false,
        searchMessage = false),

    LOADING(
        recycleView = false,
        lostConnection = false,
        notFound = false,
        progressBar = true,
        cleanHistoryButton = false,
        searchMessage = false),

    SHOW_HISTORY(
        recycleView = true,
        lostConnection = false,
        notFound = false,
        progressBar = false,
        cleanHistoryButton = true,
        searchMessage = true),

    HIDE_HISTORY(
        recycleView = true,
        lostConnection = false,
        notFound = false,
        progressBar = false,
        cleanHistoryButton = false,
        searchMessage = false),
    DEFAULT(
        recycleView = false,
        lostConnection = false,
        notFound = false,
        progressBar = false,
        cleanHistoryButton = false,
        searchMessage = false)

}