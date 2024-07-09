package com.example.playlistmaker.data.network


import com.example.playlistmaker.data.entity.Resource
import com.example.playlistmaker.domain.entity.TrackList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import java.io.IOException

class IApi(private val retrofit: Retrofit) {

    private lateinit var iApi: ItunesApi
    suspend fun search(trackName: String): Resource<TrackList> {

        iApi = retrofit.create(ItunesApi::class.java)
       return withContext(Dispatchers.IO) {
            try {
                Resource.Success(iApi.search(trackName), 200)
            } catch (e: IOException) {
                Resource.Fail(408, e.message.toString())
            } catch (e: RuntimeException) {
                Resource.Fail(408, "Connection Error")
            }
        }
    }
}