package com.example.playlistmaker.ui.main.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.databinding.ActivityMainBinding
import com.example.playlistmaker.ui.main.view_model.MainActivityViewModel
import com.example.playlistmaker.ui.main.view_model.MainActivityViewModelFactory



class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            MainActivityViewModelFactory(this)
        )[MainActivityViewModel::class.java]

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.findButton.setOnClickListener {
            viewModel.startSearch()
        }
        binding.libraryButton.setOnClickListener {
            viewModel.startLibrary()
        }
        binding.settingsButton.setOnClickListener {
            viewModel.startSettings()
        }

    }
}
