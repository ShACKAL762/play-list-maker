package com.example.playlistmaker.search

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SearchActivity : AppCompatActivity() {
    private lateinit var searchLineText: String
    private lateinit var searchLine: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var refreshButton: MaterialButton
    private lateinit var notFound: LinearLayout
    private lateinit var lostConnect: LinearLayout
    private lateinit var iApi: ItunesApi
    private val itunesURL = "https://itunes.apple.com"
    val tracks = ArrayList<Track>()
    private val retrofit = Retrofit.Builder()
        .baseUrl(itunesURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        iApi = retrofit.create(ItunesApi::class.java)
        refreshButton = findViewById(R.id.refresh)
        notFound = findViewById(R.id.not_found_message)
        lostConnect = findViewById(R.id.lost_connection_message)
        val backButton = findViewById<ImageButton>(R.id.arrow_back)
        val clearButton = findViewById<ImageView>(R.id.search_cleaner)
        searchLine = findViewById(R.id.search_line)
        searchLineText = ""
        recyclerViewInit()



        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchLineText = s.toString()
                clearButton.isVisible = clearButtonVisibility(s)


            }

            override fun afterTextChanged(s: Editable?) {
                // empty
            }
        }
        searchLine.addTextChangedListener(simpleTextWatcher)

        clearButton.setOnClickListener {
            hideKeyBoard(currentFocus)
            searchLine.setText("")
            clearRecycleView()

        }
        backButton.setOnClickListener {
            finish()
        }
        searchLine.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                search()
            }
            true
        }
        refreshButton.setOnClickListener { search() }
    }

    private fun clearRecycleView() {
            tracks.clear()
            recyclerView.adapter?.notifyDataSetChanged()

    }

    private fun search() {

        iApi.search(searchLineText).enqueue(object : Callback<TrackList> {
            override fun onResponse(call: Call<TrackList>, response: Response<TrackList>) {

                if (response.code() == 200) {
                    notFound.isVisible = false
                    lostConnect.isVisible = false
                    tracks.clear()
                    if (response.body()?.results?.isNotEmpty() == true) {
                        recyclerView.isVisible = true
                        tracks.addAll(response.body()?.results!!)
                        recyclerView.adapter?.notifyDataSetChanged()
                    }
                }
                if (tracks.isEmpty()) {
                    tracks.clear()
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
        recyclerView.adapter = RecycleAdapter(tracks)
    }

    override fun onResume() {
        super.onResume()
        searchLine.setSelection(searchLine.length())
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

    private companion object {
        const val SEARCH_TEXT = "SEARCH_TEXT"
        const val TEXT_DEF = ""
    }


}
