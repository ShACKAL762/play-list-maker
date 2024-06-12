package com.example.playlistmaker.data.network

import com.example.playlistmaker.domain.entity.TrackList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesApi {
    @GET("/search?entity=song")
    fun search(@Query("term") text: String): Call<TrackList>
}