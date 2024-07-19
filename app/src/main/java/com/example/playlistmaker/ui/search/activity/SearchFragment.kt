package com.example.playlistmaker.ui.search.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playlistmaker.databinding.SearchFragmentBinding
import com.example.playlistmaker.domain.entity.Track
import com.example.playlistmaker.ui.player.activity.PlayerActivity
import com.example.playlistmaker.ui.search.view_model.SearchViewModel
import com.example.playlistmaker.ui.search.view_model.recycleView.SearchRecycleAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchFragment : Fragment() {

    private companion object {
        private const val SEARCH_TEXT = "SEARCH_TEXT"
        private const val TEXT_DEF = ""
        private const val CLICK_DEBOUNCE_DELAY = 300L
    }

    private var tracks = mutableListOf<Track>()

    private lateinit var binding: SearchFragmentBinding
    private val viewModel: SearchViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = SearchFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchLine.setText(savedInstanceState?.getString(SEARCH_TEXT, TEXT_DEF))
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
            hideKeyBoard(requireActivity().currentFocus)
            binding.searchLine.setText("")
            showHistory()
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
        if (binding.searchLine.text.isEmpty()){
            viewModel.setHistoryVisibleState()
        }
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
        var isClickAllowed = true
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter =
            SearchRecycleAdapter(tracks) {

                lifecycleScope.launch {
                    if (isClickAllowed) {
                        isClickAllowed = false
                        val intent = Intent(requireContext(), PlayerActivity::class.java)
                        viewModel.setTrack(it)
                        requireContext().startActivity(intent)
                        delay(CLICK_DEBOUNCE_DELAY)
                        isClickAllowed = true
                    }
                }
            }
    }

    private fun clearButtonVisibility(s: CharSequence?): Boolean {
        return !s.isNullOrEmpty()
    }

    private fun hideKeyBoard(v: View?) {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(v?.windowToken, 0)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_TEXT, binding.searchLine.text.toString())
    }

    private fun observeInit() {
        viewModel.searchStateLiveData.observe(viewLifecycleOwner, Observer {
            binding.recyclerView.isVisible = it.recycleView
            binding.searchMessage.isVisible = it.searchMessage
            binding.cleanHistoryButton.isVisible = it.cleanHistoryButton
            binding.progressCircular.isVisible = it.progressBar
            binding.lostConnectionMessage.isVisible = it.lostConnection
            binding.notFoundMessage.isVisible = it.notFound
        })

        viewModel.trackListLiveData.observe(viewLifecycleOwner, Observer {
            tracks.clear()
            tracks.addAll(it)
            binding.recyclerView.adapter?.notifyDataSetChanged()
        })

    }
}