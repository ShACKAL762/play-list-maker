package com.example.playlistmaker.ui.main.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker.databinding.ActivityMainBinding
import com.example.playlistmaker.ui.main.view_model.MainActivityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class MainActivity : AppCompatActivity() {
    private val viewModel: MainActivityViewModel by viewModel{ parametersOf(this)}
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
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
