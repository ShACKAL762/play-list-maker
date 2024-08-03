package com.example.playlistmaker.ui.library.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.playlistmaker.databinding.AlbumCreateBinding

class CreateAlbumFragment :Fragment(){
    companion object{
        fun newInstance() = CreateAlbumFragment().apply {}
    }

    //private val viewModel: AlbumsListViewModel by viewModel()


    private lateinit var binding: AlbumCreateBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = AlbumCreateBinding.inflate(inflater, container, false)
        return binding.root
    }
}