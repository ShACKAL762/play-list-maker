package com.example.playlistmaker.ui.library.activity

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivityLibraryBinding
import com.example.playlistmaker.databinding.ActivityMainBinding


class LibraryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLibraryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLibraryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.arrowBack.setOnClickListener{
            finish()
        }
    }
}