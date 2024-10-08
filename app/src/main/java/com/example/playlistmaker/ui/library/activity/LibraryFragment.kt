package com.example.playlistmaker.ui.library.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.LibraryFragmentBinding
import com.example.playlistmaker.ui.library.view_models.LibraryViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator


class LibraryFragment : Fragment() {
    private lateinit var binding: LibraryFragmentBinding

    private lateinit var tabMediator: TabLayoutMediator
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = LibraryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = LibraryViewPagerAdapter(childFragmentManager,lifecycle)

        tabMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position) {
                0 -> tab.text = requireContext().getText(R.string.favorite_tracks)
                1 -> tab.text = requireContext().getText(R.string.albums)
            }
        }
        tabMediator.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tabMediator.detach()
    }
}