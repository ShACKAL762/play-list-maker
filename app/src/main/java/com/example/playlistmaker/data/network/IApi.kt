package com.example.playlistmaker.data.network


import com.example.playlistmaker.ui.models.TrackList
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class IApi(url: String) {
    private val retrofit =
        Retrofit.Builder().baseUrl(url).addConverterFactory(
            GsonConverterFactory.create()
        )
            .build()
    private lateinit var iApi: ItunesApi
    fun search(trackName: String): Response<TrackList> {
        iApi = retrofit.create(ItunesApi::class.java)

        return try {
            iApi.search(trackName).execute()
        }catch (e:Exception){
            Response.error(490,ResponseBody.create(null,"Error"))
        }
    }
}