package com.example.playlistmaker.domain.library.interactors_Impl

import com.example.playlistmaker.domain.entity.Album
import com.example.playlistmaker.domain.entity.Track
import com.example.playlistmaker.domain.library.interactors.AlbumListInteractor
import com.example.playlistmaker.domain.library.repositories.AlbumListRepository
import kotlinx.coroutines.flow.Flow

class AlbumListInteractorImpl(private val albumListRepository: AlbumListRepository): AlbumListInteractor {
    override fun getAlbumList(): Flow<List<Album>> {
        return albumListRepository.getAlbumList()
    }

    override fun getAlbum(albumId: Int): Flow<Album> {
       return albumListRepository.getAlbum(albumId)
    }

    override fun getAlbumTrackList(albumId: Int): Flow<List<Track>>{
      return albumListRepository.getTrackList(albumId)
    }

    override suspend fun deleteAlbum(album: Album){
        albumListRepository.deleteAlbum(album)
    }

    override suspend fun insertAlbum(album: Album): Long {
       return albumListRepository.insertAlbum(album)
    }

    override suspend fun addTrack(trackId: Track, albumId: Int): Long {
        return albumListRepository.addTrack(trackId,albumId)
    }

    override suspend fun deleteTrack(track: Track, albumId: Int) {
        albumListRepository.deleteTrack(track,albumId)
    }

    override suspend fun updateAlbum(album: Album) {
        albumListRepository.updateAlbum(album)
    }

    override suspend fun share(id: Int?) {
        albumListRepository.share(id)
    }
}