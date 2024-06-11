package com.example.playlistmaker.ui.search.activity

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playlistmaker.data.entity.Track
import com.example.playlistmaker.data.history.HistoryRepository
import com.example.playlistmaker.databinding.ActivitySearchBinding
import com.example.playlistmaker.ui.search.view_model.SearchViewModel
import com.example.playlistmaker.ui.search.view_model.recycleView.SearchRecycleAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchActivity : AppCompatActivity() {
    private companion object {
        private const val SEARCH_TEXT = "SEARCH_TEXT"
        private const val TEXT_DEF = ""
    }

    private var tracks = mutableListOf<Track>()

    private lateinit var binding: ActivitySearchBinding
    private val historyRepository:HistoryRepository by inject()
    private val viewModel:SearchViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)

        setContentView(binding.root)

        observeInit()
        recyclerViewInit()

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.searchLineCleaner.isVisible = clearButtonVisibility(s)
                search(s.toString())
                if (binding.searchLine.hasFocus() && s?.isEmpty() == true) {
                    showHistory()
                }
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
            binding.searchLine.setText("")
            showHistory()
        }

        binding.arrowBack.setOnClickListener {
            finish()
        }

        binding.cleanHistoryButton.setOnClickListener {
            viewModel.cleanHistory()

        }
        binding.refresh.setOnClickListener {
            search(binding.searchLine.text.toString())
        }
    }

    override fun onResume() {
        super.onResume()
        binding.searchLine.setSelection(binding.searchLine.length())
    }

    private fun showHistory() {
        viewModel.setHistoryVisibleState()
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun search(searchRequest: String) {
        binding.recyclerView.adapter?.notifyDataSetChanged()
        viewModel.writeEnd(searchRequest)
    }

    private fun recyclerViewInit() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = SearchRecycleAdapter(tracks, historyRepository)
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
        outState.putString(SEARCH_TEXT, binding.searchLine.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.searchLine.setText(savedInstanceState.getString(SEARCH_TEXT, TEXT_DEF))
    }

    private fun observeInit() {
        println(viewModel)
        viewModel.searchStateLiveData.observe(this, Observer {
            binding.recyclerView.isVisible = it.recycleView
            binding.searchMessage.isVisible = it.searchMessage
            binding.cleanHistoryButton.isVisible = it.cleanHistoryButton
            binding.progressCircular.isVisible = it.progressBar
            binding.lostConnectionMessage.isVisible = it.lostConnection
            binding.notFoundMessage.isVisible = it.notFound
        })

        viewModel.trackListLiveData.observe(this, Observer {
            tracks.clear()
            tracks.addAll(it)
            binding.recyclerView.adapter?.notifyDataSetChanged()
        })

    }
}