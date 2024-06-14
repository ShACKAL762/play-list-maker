package com.example.playlistmaker.data.network


import com.example.playlistmaker.domain.entity.TrackList
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

class IApi(private val retrofit: Retrofit) {

    private lateinit var iApi: ItunesApi
    fun search(trackName: String): Response<TrackList> {
        iApi = retrofit.create(ItunesApi::class.java)

        return try {
            iApi.search(trackName).execute()

        } catch (e: IOException) {
            Response.error(408, ResponseBody.create(null, "Connection Error"))
        } catch (e: RuntimeException) {
            Response.error(408, ResponseBody.create(null, "Connection Error"))

        }
    }
}