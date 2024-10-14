package com.example.playlistmaker.data.library

import android.content.Context
import android.content.Intent
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
import com.example.playlistmaker.utilities.trackFormat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Locale

class AlbumListRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val albumDbConverter: AlbumDbConverter,
    private val albumTrackConverter: AlbumTrackConverter,
    private val context: Context
) : AlbumListRepository {
    private val timeFormat by lazy { SimpleDateFormat("mm:ss", Locale.getDefault()) }
    override fun getAlbumList(): Flow<List<Album>> = flow {
        val albums = appDatabase.albumDao().getAlbums()
        emit(convertAlbum(albums))
    }

    override fun getTrackList(albumId: Int): Flow<List<Track>> = flow {
        emit(convertTracks(appDatabase.albumTrackListDao().getTracks(albumId).sortedBy { it.index }).reversed())
    }

    override suspend fun deleteAlbum(album: Album) {
        appDatabase.albumDao().delete(albumDbConverter.map(album))
        appDatabase.albumTrackListDao().deleteAllAlbumTracks(album.id)
        checkDeleteTrack()
    }

    override suspend fun deleteTrack(track: Track, albumId: Int) {
        appDatabase.albumTrackListDao().deleteTrack(albumTrackConverter.map(track, albumId))
        checkDeleteTrack()
        updateDB(albumId)
    }

    private suspend fun checkDeleteTrack() {
        val allTracks = appDatabase.albumTrackListDao().getTracks()
        val albums = appDatabase.albumDao().getAlbums()
        allTracks.forEach { track ->
            var inAlbum = false
            albums.forEach { album ->
                if (album.id == track.albumId) {
                    inAlbum = true
                }
            }
            if (!inAlbum) {
                appDatabase.albumTrackListDao().deleteTrack(track.trackId)
            }
        }
    }

    override suspend fun updateAlbum(album: Album) {
        appDatabase.albumDao().updateAlbum(albumDbConverter.map(album))
    }

    override fun getTrack(trackId: String): Flow<Track> = flow {
        val track = appDatabase.albumTrackListDao().getTrack(trackId)
        if (track != null) {
            val convertTrack = albumTrackConverter.map(track)
            emit(convertTrack)
        }
    }

    override suspend fun insertAlbum(album: Album): Long {

        if (!album.imageSrc.isNullOrEmpty()) {
            return appDatabase.albumDao().insertAlbum(
                albumDbConverter.map(
                    Album(
                        album.id,
                        album.name,
                        album.about,
                        album.tracksQuantity,
                        saveAlbumImage(album),
                        album.time
                    )
                )
            )
        }
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
        var maxIndex = appDatabase.albumTrackListDao().getMaxIndex()
        if (maxIndex == null)
            maxIndex = 0

        val status =
            appDatabase.albumTrackListDao()
                .insertTrack(albumTrackConverter.map(track, albumId, maxIndex + 1))

        updateDB(albumId)
        return status
    }

    private suspend fun updateDB(albumId: Int) {
        appDatabase.albumDao()
            .updateTrackQuantity(
                albumId,
                appDatabase.albumTrackListDao().getTracks(albumId).size
            )

        var albumTime = 0L
        val trackList = appDatabase.albumTrackListDao().getTracks(albumId)
        for (it in trackList) {
            albumTime += it.trackTimeMillis.toLong()
        }
        appDatabase.albumDao()
            .updateTimeQuantity(albumId, albumTime)
    }

    override fun getAlbum(albumId: Int): Flow<Album> = flow {
        emit(albumDbConverter.map(appDatabase.albumDao().getAlbum(albumId)))
    }

    override suspend fun share(id: Int?) {
        val shareData = id?.let { buildShareData(it) }
        Intent(Intent.ACTION_SEND).let {
            it.putExtra(
                Intent.EXTRA_TEXT,
                shareData
            )
            it.setType("text/plain")
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(it)
        }
    }

    private suspend fun buildShareData(id: Int): String {
        val shareAlbum = appDatabase.albumDao().getAlbum(id)
        val shareAlbumTrackList = appDatabase.albumTrackListDao().getTracks(id)
        var shareData =
            "${shareAlbum.name} \n" +
                    "${shareAlbum.about} \n" +
                    "${shareAlbum.trackQuantity}  ${
                        trackFormat(
                            shareAlbum.trackQuantity,
                            context
                        )
                    }\n\n"
        for ((index, track) in shareAlbumTrackList.withIndex()) {
            shareData = shareData.plus(
                "${index + 1}. ${track.artistName} - ${track.trackName} (${timeFormat.format(track.trackTimeMillis.toInt())})\n"
            )
        }
        return shareData
    }

    private fun convertAlbum(albums: List<AlbumDbEntity>): List<Album> {
        return albums.map { album -> albumDbConverter.map(album) }
    }

    private fun convertTracks(tracks: List<AlbumsTrackDbEntity>): List<Track> {
        return tracks.map { track -> albumTrackConverter.map(track) }
    }
}