package com.example.playlistmaker.ui.library.activity

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker.R


class LibraryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library)

        val backButton = findViewById<ImageButton>(R.id.arrow_back)
        backButton.setOnClickListener{
            finish()
        }
    }
}