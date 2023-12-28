package com.example.playlistmaker

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity


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