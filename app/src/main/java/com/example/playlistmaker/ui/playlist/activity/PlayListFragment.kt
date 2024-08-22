package com.example.playlistmaker.ui.playlist.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.PlaylistFragmentBinding
import com.example.playlistmaker.domain.entity.Album
import com.example.playlistmaker.domain.entity.Track
import com.example.playlistmaker.ui.playlist.PlayListAdapter
import com.example.playlistmaker.ui.playlist.recycleView.TrackClickListener
import com.example.playlistmaker.ui.playlist.viewModel.PlayListViewModel
import com.example.playlistmaker.ui.search.recycleView.PlaylistRecycleAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayListFragment : Fragment() {
    lateinit var binding: PlaylistFragmentBinding
    lateinit var album: Album
    private val viewModel: PlayListViewModel by viewModel()
    private val trackList = mutableListOf<Track>()
    lateinit var bottomSheetMenu: BottomSheetBehavior<ConstraintLayout>

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 300L
        private const val ALBUM_ID = "albumId"
        const val TRACK_ID = "TRACK_ID"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PlaylistFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        bottomSheetMenuInit()
        observeInit()
        recycleInit()
        viewModel.updateAlbumState(requireArguments().getInt(ALBUM_ID))
        binding.editAlbum.setOnClickListener {
            findNavController().navigate(
                R.id.action_playListFragment_to_createAlbumFragment,
                bundleOf(ALBUM_ID to album.id.toString())
            )
        }

        binding.shareAlbum.setOnClickListener {
            share()
            bottomSheetMenu.state = BottomSheetBehavior.STATE_HIDDEN
        }
        binding.shareButton.setOnClickListener {
            share()
        }
        binding.moreButton.setOnClickListener {
            bottomSheetMenu.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        binding.shadow.setOnClickListener {
            bottomSheetMenu.state = BottomSheetBehavior.STATE_HIDDEN
        }
        binding.deleteAlbum.setOnClickListener {
            dialogDeleteAlbum(album)
        }
        binding.arrowBack.setOnClickListener {
            findNavController().popBackStack()
        }



        super.onViewCreated(view, savedInstanceState)


    }

    private fun bottomSheetMenuInit() {
        bottomSheetMenu = BottomSheetBehavior.from(binding.bottomSheetMenu)

        bottomSheetMenu.state = BottomSheetBehavior.STATE_HIDDEN
        bottomSheetMenu.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {

                    BottomSheetBehavior.STATE_HIDDEN -> {
                        binding.shadow.isVisible = false
                    }

                    else -> {
                        binding.shadow.isVisible = true
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
    }

    private fun share() {
        if (trackList.isEmpty()) {
            Toast.makeText(requireContext(), requireContext().getString(R.string.empty_playlist), Toast.LENGTH_SHORT).show()
        } else {
            viewModel.share()
        }
    }


    private fun observeInit() {

        viewModel.albumLiveData.observe(viewLifecycleOwner) {
            PlayListAdapter(binding).onBindPlayerHolder(it)
            album = it
        }
        viewModel.trackListLiveData.observe(viewLifecycleOwner) {
            trackList.clear()
            trackList.addAll(it)
            binding.recyclerView.adapter?.notifyDataSetChanged()
            if (trackList.isEmpty())
                binding.emptyPlayList.isVisible = true
            else binding.emptyPlayList.isVisible = false
        }
    }

    private fun recycleInit() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerView.adapter = PlaylistRecycleAdapter(trackList,
            object : TrackClickListener {
                override fun onLongTrackClick(track: Track): Boolean {
                    dialogDeleteTrack(track)
                    return true
                }

                override fun onTrackClick(track: Track) {
                    var isClickAllowed = true
                    lifecycleScope.launch {
                        if (isClickAllowed) {
                            isClickAllowed = false

                            val bundle = bundleOf(TRACK_ID to track.trackId)
                            findNavController().navigate(
                                R.id.action_playListFragment_to_playerActivity,
                                bundle
                            )
                            delay(CLICK_DEBOUNCE_DELAY)
                            isClickAllowed = true
                        }
                    }
                }

            }
        )

    }

    private fun dialogDeleteTrack(track: Track) {
        MaterialAlertDialogBuilder(requireContext(), R.style.DeleteAlertText)
            .setTitle(requireContext().getString(R.string.delete_track_title))
            .setMessage(requireContext().getString(R.string.delete_track))
            .setNegativeButton(requireContext().getString(R.string.cancel)) { _, _ ->
            }
            .setPositiveButton(requireContext().getString(R.string.delete)) { _, _ ->
                viewModel.deleteTrack(track)
            }
            .show()

    }

    private fun dialogDeleteAlbum(album: Album) {
        MaterialAlertDialogBuilder(requireContext(), R.style.DeleteAlertText)
            .setTitle("")
            .setMessage("${requireContext().getString(R.string.delete_album)} \"${album.name}\"?")
            .setNegativeButton(requireContext().getString(R.string.no)) { _, _ ->
            }
            .setPositiveButton(requireContext().getString(R.string.yes)) { _, _ ->
                viewModel.deleteAlbum(album)
                findNavController().popBackStack()
            }
            .show()
    }
}


