package com.example.playlistmaker.ui.playlist.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
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
    val viewModel: PlayListViewModel by viewModel()
    lateinit var album: Album
    val trackList = mutableListOf<Track>()
    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 300L
        private const val ALBUM_ID = "albumId"
        const val TRACK_ID = "TRACK_ID"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PlaylistFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheetMenu)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        observeInit()
        recycleInit()


        viewModel.updateAlbumState(requireArguments().getInt(ALBUM_ID))
        binding.moreButton.setOnClickListener {

            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        binding.shareButton.setOnClickListener {

        }

        super.onViewCreated(view, savedInstanceState)


    }


    private fun observeInit() {

        viewModel.albumLiveData.observe(viewLifecycleOwner) {
            PlayListAdapter(binding).onBindPlayerHolder(it)
        }
        viewModel.trackListLiveData.observe(viewLifecycleOwner) {
            trackList.clear()
            trackList.addAll(it)
            binding.recyclerView.adapter?.notifyDataSetChanged()
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
            .setTitle("")
            .setMessage("Хотите удалить трек?")
            .setNegativeButton(requireContext().getString(R.string.no)) { _, _ ->
            }
            .setPositiveButton(requireContext().getString(R.string.yes)) { _, _ ->
                viewModel.deleteTrack(track)
            }
            .show()
    }
}


