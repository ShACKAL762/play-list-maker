package com.example.playlistmaker.ui.search.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playlistmaker.domain.entity.History
import com.example.playlistmaker.data.network.ItunesApi
import com.example.playlistmaker.data.search.SearchHistoryRepository
import com.example.playlistmaker.databinding.ActivitySearchBinding
import com.example.playlistmaker.domain.entity.Track
import com.example.playlistmaker.ui.models.TrackList
import com.example.playlistmaker.ui.search.SearchRecycleAdapter
import com.example.playlistmaker.ui.search.view_model.SearchViewModel
import com.example.playlistmaker.ui.search.view_model.SearchViewModel.SViewState
import com.example.playlistmaker.ui.search.view_model.SearchViewModelFactory
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

    private lateinit var iApi: ItunesApi

    private var tracks = mutableListOf<Track>()
    private val retrofit =
        Retrofit.Builder().baseUrl(ITUNES_URL).addConverterFactory(GsonConverterFactory.create())
            .build()

    private val handler = Handler(Looper.getMainLooper())

    private val searchEvent = Runnable { search() }

    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: SearchViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        viewModel =
            ViewModelProvider(this, SearchViewModelFactory(this))[SearchViewModel::class.java]

        setContentView(binding.root)
        observeInit()

//todo
        iApi = retrofit.create(ItunesApi::class.java)

        recyclerViewInit()


        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setSearchLineData(s.toString())
                binding.searchLineCleaner.isVisible = clearButtonVisibility(s)

                //TODO{
                writeTextEnd()
                //TODO}

                if (binding.searchLine.hasFocus() && s?.isEmpty() == true) {
                    showHistory()
                }else unShowHistory()
            }

            override fun afterTextChanged(s: Editable?) {
                // empty
            }
        }
        binding.searchLine.addTextChangedListener(simpleTextWatcher)




        binding.searchLine.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && binding.searchLine.text.isEmpty()) {
                showHistory()
            }
        }

        binding.searchLineCleaner.setOnClickListener {
            hideKeyBoard(currentFocus)
            viewModel.setSearchLineData("")
            binding.searchLine.setText("")
            viewModel.setHistoryVisibleState()

        }
        binding.arrowBack.setOnClickListener {
            finish()
        }


        binding.cleanHistoryButton.setOnClickListener {
            viewModel.cleanHistory()
            //todo
            clearRecycleView()
            unShowHistory()
        }


    }

    private fun observeInit() {

        viewModel.recycleView.observe(this, Observer {
            binding.recyclerView.isVisible = it
        })

        viewModel.notFoundPlaceholder.observe(this, Observer {
            binding.notFoundMessage.isVisible = it
        })

        viewModel.progressBar.observe(this, Observer {
            binding.progressCircular.isVisible = it
        })

        viewModel.lostConnectionPlaceholder.observe(this, Observer {
            binding.lostConnectionMessage.isVisible = it
        })
        viewModel.cleanHistoryButton.observe(this, Observer {
            binding.cleanHistoryButton.isVisible = it
        })
        viewModel.historyMessage.observe(this, Observer {
            binding.searchMessage.isVisible = it
        })
    }

    private fun writeTextEnd() {
        handler.removeCallbacks(searchEvent)
        if (binding.searchLine.text.isNotEmpty()) {
            handler.postDelayed(searchEvent, SEARCH_DELAY.toLong())
        } else viewModel.changeState(SViewState.LOAD)
    }

    override fun onResume() {
        super.onResume()
        binding.searchLine.setSelection(binding.searchLine.length())


    }

    private fun unShowHistory() {
        viewModel.changeState(SViewState.HIDE_HISTORY)
        binding.recyclerView.adapter = SearchRecycleAdapter(tracks)
        tracks.clear()
        binding.recyclerView.adapter?.notifyDataSetChanged()

    }

    private fun showHistory() {
        viewModel.setHistoryVisibleState()
        updateHistory()


    }

    private fun updateHistory() {
        val x = SearchHistoryRepository(this)
        binding.recyclerView.adapter = SearchRecycleAdapter(tracks)
        tracks.clear()
        tracks.addAll(x.getHistoryList())
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun clearRecycleView() {
        tracks.clear()
        binding.recyclerView.adapter?.notifyDataSetChanged()

    }

    private fun search() {
        viewModel.changeState(SViewState.LOAD)

        iApi.search(viewModel.searchLineLiveData.value.toString())
            .enqueue(object : Callback<TrackList> {
                override fun onResponse(call: Call<TrackList>, response: Response<TrackList>) {

                    if (response.code() == 200) {
                        viewModel.changeState(SViewState.SUCCESS)
                        tracks.clear()

                        val result = response.body()?.results
                        if (result?.isNotEmpty() == true) {
                            binding.recyclerView.isVisible = true
                            tracks.addAll(response.body()?.results!!)
                            binding.recyclerView.adapter?.notifyDataSetChanged()
                        }
                    }
                    if (tracks.isEmpty()) {
                        binding.recyclerView.adapter?.notifyDataSetChanged()
                        viewModel.changeState(SViewState.NOT_FOUND)
                    }
                }

                override fun onFailure(call: Call<TrackList>, t: Throwable) {
                    tracks.clear()
                    binding.recyclerView.adapter?.notifyDataSetChanged()
                    viewModel.changeState(SViewState.LOST_CONNECTION)
                }
            })

    }

    private fun recyclerViewInit() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = SearchRecycleAdapter(tracks)
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
        outState.putString(SEARCH_TEXT, viewModel.searchLineLiveData.value)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.searchLine.setText(savedInstanceState.getString(SEARCH_TEXT, TEXT_DEF))
    }
}