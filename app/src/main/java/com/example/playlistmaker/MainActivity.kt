package com.example.playlistmaker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val findButton = findViewById<Button>(R.id.find_button)
        val libraryButton = findViewById<Button>(R.id.library_button)
        val settingsButton = findViewById<Button>(R.id.settings_button)

        findButton.setOnClickListener {
            startActivity(Intent(this,FindActivity::class.java))
        }
        libraryButton.setOnClickListener {
            startActivity(Intent(this,LibraryActivity::class.java))
        }
        settingsButton.setOnClickListener{
            startActivity(Intent(this, SettingsActivity::class.java))
        }

    }
}
