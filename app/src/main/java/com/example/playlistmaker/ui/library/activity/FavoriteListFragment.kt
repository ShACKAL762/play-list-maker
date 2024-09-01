package com.example.playlistmaker.ui.library.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FavoriteFragmentBinding
import com.example.playlistmaker.domain.entity.Track
import com.example.playlistmaker.ui.library.view_models.FavoriteListFragmentViewModel
import com.example.playlistmaker.ui.library.view_models.recycleView.LibraryRecycleAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteListFragment : Fragment() {
    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 300L
        const val TRACK_ID = "TRACK_ID"
        fun newInstance() = FavoriteListFragment().apply {
        }

    }

    private var tracks = mutableListOf<Track>()
    private val viewModel: FavoriteListFragmentViewModel by viewModel()


    private lateinit var binding: FavoriteFragmentBinding
    override fun onResume() {
        viewModel.updateList()
        super.onResume()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FavoriteFragmentBinding.inflate(inflater, container, false)
        observeInit()
        recyclerViewInit()
        return binding.root
    }

    private fun recyclerViewInit() {
        var isClickAllowed = true
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter =
            LibraryRecycleAdapter(tracks) {

                lifecycleScope.launch {
                    if (isClickAllowed) {
                        isClickAllowed = false

                        val bundle = bundleOf(TRACK_ID to it.trackId)
                        findNavController().navigate(R.id.action_libraryFragment_to_playerActivity,bundle)

                        delay(CLICK_DEBOUNCE_DELAY)
                        isClickAllowed = true
                    }
                }
            }
    }

    private fun observeInit() {
        viewModel.libraryStateLiveData.observe(viewLifecycleOwner, Observer {
            binding.recyclerView.isVisible = it
            binding.placeHolder.isVisible = !it
            binding.textMessage.isVisible = !it
        })

        viewModel.trackListLiveData.observe(viewLifecycleOwner, Observer {
            tracks.clear()
            tracks.addAll(it)
            binding.recyclerView.adapter?.notifyDataSetChanged()
        })

    }
}