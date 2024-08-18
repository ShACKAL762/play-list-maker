package com.example.playlistmaker.data.library

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import androidx.core.net.toUri
import com.example.playlistmaker.data.converters.AlbumDbConverter
import com.example.playlistmaker.data.converters.AlbumTrackConverter
import com.example.playlistmaker.data.db.AppDatabase
import com.example.playlistmaker.data.db.entity.AlbumDbEntity
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

    override suspend fun deleteAlbum(album: Album) {
        appDatabase.albumDao().delete(albumDbConverter.map(album))
    }

    override suspend fun insertAlbum(album: Album):Long {

        if (album.imageSrc != Uri.EMPTY)
            album.imageSrc = saveAlbumImage(album)
        return appDatabase.albumDao().insertAlbum(albumDbConverter.map(album))
    }

    private fun saveAlbumImage(album: Album): Uri {
        val filePath = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "myalbum")
        //создаем каталог, если он не создан
        if (!filePath.exists()) {
            filePath.mkdirs()
        }

        //создаём экземпляр класса File, который указывает на файл внутри каталога
        val file = File(filePath, "${album.name}.jpg")//Todo от имени файла
        // создаём входящий поток байтов из выбранной картинки
        val inputStream = context.contentResolver.openInputStream(album.imageSrc!!)
        // создаём исходящий поток байтов в созданный выше файл
        val outputStream = FileOutputStream(file)
        // записываем картинку с помощью BitmapFactory
        BitmapFactory
            .decodeStream(inputStream)
            .compress(Bitmap.CompressFormat.JPEG, 30, outputStream)
        return file.toUri()

    }

    override suspend fun addTrack(track: Track, albumId: Int):Long {

        val status = appDatabase.albumTrackListDao().insertTrack(albumTrackConverter.map(track,albumId))
        appDatabase.albumDao().updateTrackQuantity(albumId,appDatabase.albumTrackListDao().getTracks(albumId).size)
        return status
    }

    private fun convertAlbum(albums: List<AlbumDbEntity>): List<Album> {
        return albums.map { album -> albumDbConverter.map(album) }
    }
}