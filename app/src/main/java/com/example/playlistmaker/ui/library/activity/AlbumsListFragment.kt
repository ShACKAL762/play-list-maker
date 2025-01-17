package com.example.playlistmaker.ui.library.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.AlbumFragmentBinding
import com.example.playlistmaker.domain.entity.Album
import com.example.playlistmaker.ui.library.view_models.AlbumsListViewModel
import com.example.playlistmaker.ui.library.view_models.recycleView.AlbumRecycleAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlbumsListFragment : Fragment() {
    companion object {
        const val ALBUM_ID = "albumId"
        const val CLICK_DEBOUNCE_DELAY = 2000L
        fun newInstance() = AlbumsListFragment().apply {}
    }

    private val viewModel: AlbumsListViewModel by viewModel()
    private var albums: MutableList<Album> = mutableListOf()


    private lateinit var binding: AlbumFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AlbumFragmentBinding.inflate(inflater, container, false)
        binding.createPlayList.setOnClickListener {
            findNavController().navigate(R.id.action_libraryFragment_to_createAlbumFragment,
                bundleOf()
            )
        }
        observeInit()
        recycleViewInit()
        return binding.root
    }


    private fun observeInit() {
        viewModel.albumListLiveData.observe(viewLifecycleOwner) {
            albums.clear()
            albums.addAll(it)
            binding.recyclerView.adapter?.notifyDataSetChanged()
        }
        viewModel.albumListViewStateLiveData.observe(viewLifecycleOwner) {
            binding.recyclerView.isVisible = it
            binding.placeHolder.isVisible = !it
            binding.placeHolderText.isVisible = !it
        }
    }

    private fun recycleViewInit() {
        var isClickAllowed = true
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.adapter =
            AlbumRecycleAdapter(albums) {

                findNavController().navigate(R.id.action_libraryFragment_to_playListFragment,
                    bundleOf(ALBUM_ID to it.id)
                )
                lifecycleScope.launch {
                    if (isClickAllowed) {
                        isClickAllowed = false
                        delay(CLICK_DEBOUNCE_DELAY)
                        isClickAllowed = true
                    }
                }
            }
    }

    override fun onResume() {
        viewModel.updateAlbumList()
        binding.recyclerView.adapter?.notifyDataSetChanged()
        super.onResume()
    }

}
