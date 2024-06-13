package com.example.playlistmaker.ui.library.activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.playlistmaker.databinding.FavoriteFragmentBinding
import com.example.playlistmaker.ui.library.activity.view_models.AlbumsListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteListFragment : Fragment() {
    private val viewModel: AlbumsListViewModel by viewModel()

    private lateinit var binding: FavoriteFragmentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FavoriteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}