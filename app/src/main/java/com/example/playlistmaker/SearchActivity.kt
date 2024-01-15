package com.example.playlistmaker

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView


class SearchActivity : AppCompatActivity() {
    private lateinit var searchLine:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


        val backButton = findViewById<ImageButton>(R.id.arrow_back)
        val clearButton = findViewById<ImageView>(R.id.search_cleaner)
        searchLine = findViewById(R.id.search_line)

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

        clearButton.setOnClickListener {
            hideKeyBoard(currentFocus)
            searchLine.setText("")

        }
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
    private fun hideKeyBoard(v: View?) {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(v?.windowToken, 0)
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        println("save")
        outState.putString(SEARCH_TEXT, searchLine.text.toString())
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        println("restore")
        searchLine.setText(savedInstanceState.getString(SEARCH_TEXT, TEXT_DEF))
    }
    companion object{
       const val SEARCH_TEXT = "SEARCH_TEXT"
       const val TEXT_DEF = ""
   }


}
