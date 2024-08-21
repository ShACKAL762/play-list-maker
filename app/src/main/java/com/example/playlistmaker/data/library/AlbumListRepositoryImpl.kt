package com.example.playlistmaker.data.library

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import com.example.playlistmaker.data.converters.AlbumDbConverter
import com.example.playlistmaker.data.converters.AlbumTrackConverter
import com.example.playlistmaker.data.db.AppDatabase
import com.example.playlistmaker.data.db.entity.AlbumDbEntity
import com.example.playlistmaker.data.db.entity.AlbumsTrackDbEntity
import com.example.playlistmaker.domain.entity.Album
import com.example.playlistmaker.domain.entity.Track
import com.example.playlistmaker.domain.library.repositories.AlbumListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.io.FileOutputStream

class AlbumListRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val albumDbConverter: AlbumDbConverter,
    private val albumTrackConverter: AlbumTrackConverter,
    private val context: Context
) : AlbumListRepository {
    override fun getAlbumList(): Flow<List<Album>> = flow {
        val albums = appDatabase.albumDao().getAlbums()
        emit(convertAlbum(albums))
    }

    override fun getTrackList(albumId: Int): Flow<List<Track>> = flow {
        emit(convertTracks(appDatabase.albumTrackListDao().getTracks(albumId)))
    }

    override suspend fun deleteAlbum(album: Album) {
        appDatabase.albumDao().delete(albumDbConverter.map(album))
    }

    override suspend fun deleteTrack(track: Track, albumId: Int) {
        appDatabase.albumTrackListDao().deleteTrack(albumTrackConverter.map(track,albumId))
        updateDB(albumId,1)
    }

    override suspend fun insertAlbum(album: Album): Long {

        if (!album.imageSrc.isNullOrEmpty())
            album.imageSrc = saveAlbumImage(album)
        return appDatabase.albumDao().insertAlbum(albumDbConverter.map(album))
    }

    private fun saveAlbumImage(album: Album): String {
        val filePath = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "myalbum")
        if (!filePath.exists()) {
            filePath.mkdirs()
        }


        val file = File(filePath, "${album.name}.jpg")
        val inputStream = context.contentResolver.openInputStream(Uri.parse(album.imageSrc!!))
        val outputStream = FileOutputStream(file)
        BitmapFactory
            .decodeStream(inputStream)
            .compress(Bitmap.CompressFormat.JPEG, 30, outputStream)
        return file.toString()

    }

    override suspend fun addTrack(track: Track, albumId: Int): Long {

        val status =
            appDatabase.albumTrackListDao().insertTrack(albumTrackConverter.map(track, albumId))

        updateDB(albumId,status)
            return status
    }

    private suspend fun updateDB(albumId: Int,status:Long) {
        if (status != -1L){
            appDatabase.albumDao()
                .updateTrackQuantity(albumId, appDatabase.albumTrackListDao().getTracks(albumId).size)

            var albumTime = 0L
            val trackList = appDatabase.albumTrackListDao().getTracks(albumId)
            for (it in trackList){
                albumTime += it.trackTimeMillis.toLong()
            }
            appDatabase.albumDao()
                .updateTimeQuantity(albumId, albumTime)
        }
    }

    override fun getAlbum(albumId: Int): Flow<Album> = flow {
        emit(albumDbConverter.map(appDatabase.albumDao().getAlbum(albumId)))
    }



    private fun convertAlbum(albums: List<AlbumDbEntity>): List<Album> {
        return albums.map { album -> albumDbConverter.map(album) }
    }
    private fun convertTracks(tracks: List<AlbumsTrackDbEntity>): List<Track> {
        return tracks.map { track -> albumTrackConverter.map(track) }
    }
}