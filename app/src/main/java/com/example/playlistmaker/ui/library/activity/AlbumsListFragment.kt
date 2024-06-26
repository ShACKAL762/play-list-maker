package com.example.playlistmaker.ui.library.activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.playlistmaker.databinding.AlbumFragmentBinding
import com.example.playlistmaker.ui.library.activity.view_models.AlbumsListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
class AlbumsListFragment : Fragment() {
    companion object{
    fun newInstance() = AlbumsListFragment().apply {
    }

    }

    private val viewModel: AlbumsListViewModel by viewModel()


    private lateinit var binding: AlbumFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = AlbumFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}