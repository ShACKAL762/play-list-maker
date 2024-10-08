package com.example.playlistmaker.ui.library.view_models

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.playlistmaker.ui.library.activity.AlbumsListFragment
import com.example.playlistmaker.ui.library.activity.FavoriteListFragment

class LibraryViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> FavoriteListFragment.newInstance()
            else -> AlbumsListFragment.newInstance()
        }
    }
}