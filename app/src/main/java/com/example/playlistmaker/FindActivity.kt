package com.example.playlistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EdgeEffect
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView


class FindActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find)

        val backButton = findViewById<ImageButton>(R.id.arrow_back)
        val searchLine = findViewById<EditText>(R.id.search_line)
        val clearButton = findViewById<ImageView>(R.id.search_cleaner)

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearButton.visibility = clearButtonVisibility(s)
            }

            override fun afterTextChanged(s: Editable?) {
                // empty
            }
        }
        searchLine.addTextChangedListener(simpleTextWatcher)

        clearButton.setOnClickListener { searchLine.setText("") }
        backButton.setOnClickListener{
            finish()
        }
    }
    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
}