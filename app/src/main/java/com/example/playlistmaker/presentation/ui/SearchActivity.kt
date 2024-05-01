package com.example.playlistmaker.presentation.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.domain.entity.History
import com.example.playlistmaker.data.network.ItunesApi
import com.example.playlistmaker.presentation.SearchRecycleAdapter
import com.example.playlistmaker.data.SearchHistory
import com.example.playlistmaker.domain.entity.Track
import com.example.playlistmaker.presentation.models.TrackList
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SearchActivity : AppCompatActivity() {

    private companion object {
        private const val SEARCH_TEXT = "SEARCH_TEXT"
        private const val TEXT_DEF = ""

        private const val SEARCH_DELAY = 2000
        private const val ITUNES_URL = "https://itunes.apple.com"

    }


    private lateinit var searchLineText: String

    private lateinit var searchLine: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var notFound: LinearLayout
    private lateinit var lostConnect: LinearLayout
    private lateinit var searchMessage: TextView
    private lateinit var progressBar: ProgressBar

    private lateinit var refreshButton: MaterialButton
    private lateinit var cleanHistoryButton: MaterialButton
    private lateinit var clearButton: ImageView
    private lateinit var backButton: ImageButton


    private lateinit var iApi: ItunesApi

    private var tracks = mutableListOf<Track>()
    private val searchHistory = SearchHistory()
    private val retrofit =
        Retrofit.Builder().baseUrl(ITUNES_URL).addConverterFactory(GsonConverterFactory.create())
            .build()

    private val handler = Handler(Looper.getMainLooper())

    private val searchEvent = Runnable { search() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        iApi = retrofit.create(ItunesApi::class.java)

        clearButton = findViewById(R.id.search_line_cleaner)
        backButton = findViewById(R.id.arrow_back)
        searchMessage = findViewById(R.id.search_message)
        cleanHistoryButton = findViewById(R.id.clean_history_button)
        refreshButton = findViewById(R.id.refresh)
        notFound = findViewById(R.id.not_found_message)
        lostConnect = findViewById(R.id.lost_connection_message)
        searchLine = findViewById(R.id.search_line)
        progressBar = findViewById(R.id.progress_circular)

        searchLineText = ""

        recyclerViewInit()


        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchLineText = s.toString()
                clearButton.isVisible = clearButtonVisibility(s)
                writeTextEnd()

                when (searchLine.hasFocus() && s?.isEmpty() == true && searchHistory.getHistoryList(
                    applicationContext
                ).size > 0) {

                    true -> showHistory()

                    false -> unShowHistory()

                }
            }

            override fun afterTextChanged(s: Editable?) {
                // empty
            }
        }
        searchLine.addTextChangedListener(simpleTextWatcher)


        searchLine.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && searchLine.text.isEmpty()
                && searchHistory.getHistoryList(applicationContext).size > 0
            ) {
                showHistory()
            }
        }

        clearButton.setOnClickListener {
            hideKeyBoard(currentFocus)
            searchLine.setText("")
            showHistory()

        }
        backButton.setOnClickListener {
            finish()
        }


        cleanHistoryButton.setOnClickListener {
            getSharedPreferences("history", MODE_PRIVATE).edit()
                .putString("history", Gson().toJson(History(mutableListOf()), History::class.java))
                .apply()
            clearRecycleView()
            unShowHistory()
        }


    }

    private fun writeTextEnd() {
        handler.removeCallbacks(searchEvent)
        if (searchLine.text.isNotEmpty()) {
            handler.postDelayed(searchEvent, SEARCH_DELAY.toLong())
        } else progressBar.isVisible = false
    }

    override fun onResume() {
        super.onResume()
        searchLine.setSelection(searchLine.length())


    }

    private fun unShowHistory() {
        cleanHistoryButton.isVisible = false
        searchMessage.isVisible = false
        recyclerView.adapter = SearchRecycleAdapter(tracks)
        tracks.clear()
        recyclerView.adapter?.notifyDataSetChanged()
        recyclerView.isVisible = true

    }

    private fun showHistory() {
        cleanHistoryButton.isVisible = true
        searchMessage.isVisible = true
        updateHistory()
        recyclerView.isVisible = true
        lostConnect.isVisible = false
        notFound.isVisible = false

    }

    private fun updateHistory() {
        recyclerView.adapter = SearchRecycleAdapter(tracks)
        tracks.clear()
        tracks.addAll(searchHistory.getHistoryList(this))
        recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun clearRecycleView() {
        tracks.clear()
        recyclerView.adapter?.notifyDataSetChanged()

    }

    private fun search() {

        progressBar.isVisible = true
        iApi.search(searchLineText).enqueue(object : Callback<TrackList> {
            override fun onResponse(call: Call<TrackList>, response: Response<TrackList>) {
                progressBar.isVisible = false

                if (response.code() == 200) {
                    notFound.isVisible = false
                    lostConnect.isVisible = false
                    tracks.clear()

                    val result = response.body()?.results
                    if (result?.isNotEmpty() == true) {
                        recyclerView.isVisible = true
                        tracks.addAll(response.body()?.results!!)
                        recyclerView.adapter?.notifyDataSetChanged()
                    }
                }
                if (tracks.isEmpty()) {
                    recyclerView.adapter?.notifyDataSetChanged()
                    recyclerView.isVisible = false
                    lostConnect.isVisible = false
                    notFound.isVisible = true
                }
            }

            override fun onFailure(call: Call<TrackList>, t: Throwable) {
                tracks.clear()
                recyclerView.adapter?.notifyDataSetChanged()
                recyclerView.isVisible = false
                notFound.isVisible = false
                lostConnect.isVisible = true
            }
        })

    }

    private fun recyclerViewInit() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SearchRecycleAdapter(tracks)
    }

    private fun clearButtonVisibility(s: CharSequence?): Boolean {
        return !s.isNullOrEmpty()
    }

    private fun hideKeyBoard(v: View?) {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(v?.windowToken, 0)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_TEXT, searchLineText)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        searchLine.setText(savedInstanceState.getString(SEARCH_TEXT, TEXT_DEF))
    }
}