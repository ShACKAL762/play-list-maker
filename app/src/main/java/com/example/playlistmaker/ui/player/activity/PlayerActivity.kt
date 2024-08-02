package com.example.playlistmaker.ui.player.activity


import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivityPlayerBinding
import com.example.playlistmaker.ui.player.PlayerAdapter
import com.example.playlistmaker.ui.player.view_model.PlayerViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayerActivity : AppCompatActivity() {
    companion object {
        const val TRACK_ID = "TRACK_ID"
    }

    private lateinit var binding: ActivityPlayerBinding
    private val viewModel: PlayerViewModel by viewModel()
    private lateinit var playerAdapter: PlayerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityPlayerBinding.inflate(layoutInflater)
        playerAdapter = PlayerAdapter(binding)
        Log.e("Test","${intent.getStringExtra(TRACK_ID)}")
        intent.getStringExtra(TRACK_ID)?.let { viewModel.prepareTrack(it) }
        observeInit()

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.play.setOnClickListener {
            viewModel.control()
        }

        binding.arrowBack.setOnClickListener {
            stopActivity()
        }

        binding.like.setOnClickListener {
            viewModel.likeEvent()
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                stopActivity()
            }
        })
    }

    private fun observeInit() {
        viewModel.currentTimeLiveData.observe(this, Observer {
            binding.playTime.text = it
        })
        viewModel.likeStateLiveData.observe(this, Observer {
            if (it) {
                binding.like.setImageResource(R.drawable.like_button)
            } else {
                binding.like.setImageResource(R.drawable.dislike_button)
            }
        })

        viewModel.buttonStateLiveData.observe(this, Observer {
            if (it) {
                binding.play.setImageResource(R.drawable.pause_button)
            } else {
                binding.play.setImageResource(R.drawable.play_button)
            }
        })

        viewModel.trackLiveData.observe(this, Observer {
            playerAdapter.onBindPlayerHolder(it)
        })
    }

    override fun onPause() {
        viewModel.pause()
        super.onPause()
    }

    private fun stopActivity() {
        finish()
    }
}