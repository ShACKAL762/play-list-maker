package com.example.playlistmaker.player

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker.R
import com.example.playlistmaker.search.SearchHistory

class PlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        val history = SearchHistory()

        val playerAdapter = PlayerAdapter(this)

        val list = history.getHistoryList(this)
        playerAdapter.onBindPlayerHolder(list)

        val backButton = findViewById<ImageButton>(R.id.arrow_back)
        backButton.setOnClickListener {
            finish()
        }
    }
}



