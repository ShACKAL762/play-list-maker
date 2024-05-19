package com.example.playlistmaker.ui.player.activity


import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivityPlayerBinding
import com.example.playlistmaker.ui.player.PlayerAdapter
import com.example.playlistmaker.ui.player.view_model.PlayerViewModel
import com.example.playlistmaker.ui.player.view_model.PlayerViewModelFactory

class PlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerBinding
    private lateinit var viewModel: PlayerViewModel
    private lateinit var playerAdapter: PlayerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel =
            ViewModelProvider(
                this, PlayerViewModelFactory(this)
            )[PlayerViewModel::class.java]

        binding = ActivityPlayerBinding.inflate(layoutInflater)
        playerAdapter = PlayerAdapter(this)

        observeInit()

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.play.setOnClickListener {
            viewModel.control()
        }

        binding.arrowBack.setOnClickListener {
            stopActivity()
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