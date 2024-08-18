package com.example.playlistmaker.ui.player.activity


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivityPlayerBinding
import com.example.playlistmaker.domain.entity.Album
import com.example.playlistmaker.ui.player.PlayerAdapter
import com.example.playlistmaker.ui.player.view_model.PlayerViewModel
import com.example.playlistmaker.ui.player.view_model.recycle_view.PlayerRecycleAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayerFragment : Fragment() {
    companion object {
        const val TRACK_ID = "TRACK_ID"
        const val CLICK_DEBOUNCE_DELAY = 2000L
    }

    private lateinit var binding: ActivityPlayerBinding
    private val viewModel: PlayerViewModel by viewModel()
    private lateinit var playerAdapter: PlayerAdapter
    private val albums = mutableListOf<Album>()
    private lateinit var bottomSheet:BottomSheetBehavior<LinearLayout>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ActivityPlayerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playerAdapter = PlayerAdapter(binding)
        viewModel.prepareTrack(requireArguments().getString(TRACK_ID).toString())

        observeInit()
        recyclerViewInit()


        bottomSheet = BottomSheetBehavior.from(binding.bottomSheet)
        bottomSheet.state = BottomSheetBehavior.STATE_HIDDEN
        binding.createPlayList.setOnClickListener {
            findNavController().navigate(R.id.action_playerActivity_to_createAlbumFragment)
        }


        binding.play.setOnClickListener {
            viewModel.control()
        }

        binding.arrowBack.setOnClickListener {
            stopActivity()
        }

        binding.like.setOnClickListener {
            viewModel.likeEvent()
        }
        binding.queue.setOnClickListener {
            bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        bottomSheet.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        binding.shadow.isVisible = false
                    }

                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        viewModel.updateList()
                    }

                    else -> binding.shadow.isVisible = true


                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

        })

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })
    }

    private fun observeInit() {
        viewModel.currentTimeLiveData.observe(viewLifecycleOwner, Observer {
            binding.playTime.text = it
        })
        viewModel.likeStateLiveData.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.like.setImageResource(R.drawable.like_button)
            } else {
                binding.like.setImageResource(R.drawable.dislike_button)
            }
        })

        viewModel.buttonStateLiveData.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.play.setImageResource(R.drawable.pause_button)
            } else {
                binding.play.setImageResource(R.drawable.play_button)
            }
        })

        viewModel.trackLiveData.observe(viewLifecycleOwner, Observer {
            playerAdapter.onBindPlayerHolder(it)
        })
        viewModel.albumListLiveData.observe(viewLifecycleOwner, Observer {
            albums.clear()
            albums.addAll(it)
            binding.recyclerView.adapter?.notifyDataSetChanged()
        })
        viewModel.insertStatusLiveData.observe(viewLifecycleOwner, Observer {
            when(it.success){
                true -> {
                    bottomSheet.state = BottomSheetBehavior.STATE_HIDDEN
                    Toast.makeText(requireContext(),"${R.string.track_added} ${it.albumName}.",Toast.LENGTH_SHORT).show()
                }
                false -> {Toast.makeText(requireContext(),"${R.string.track_added_yet} ${it.albumName}.",Toast.LENGTH_SHORT).show()}
            }

        })
    }

    override fun onPause() {
        viewModel.pause()
        super.onPause()
    }

    private fun stopActivity() {
        findNavController().popBackStack()
    }

    private fun recyclerViewInit() {
        var isClickAllowed = true
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter =
            PlayerRecycleAdapter(albums) {

                viewModel.insertTrack(it)

                binding.recyclerView.adapter?.notifyDataSetChanged()
                lifecycleScope.launch {
                    if (isClickAllowed) {
                        isClickAllowed = false
                        delay(CLICK_DEBOUNCE_DELAY)
                        isClickAllowed = true

                    }
                }
            }
    }
}