package com.example.playlistmaker.ui.settings.activity


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.playlistmaker.databinding.SettingsFragmentBinding
import com.example.playlistmaker.ui.settings.view_model.SettingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingFragment : Fragment() {

    private val viewModel: SettingViewModel by viewModel()
    private lateinit var binding: SettingsFragmentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = SettingsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getTheme()

        binding.shareButton.setOnClickListener {
            println("click")
            viewModel.share()
        }

        binding.supportButton.setOnClickListener {
            viewModel.sendSupport()
        }

        binding.eulaButton.setOnClickListener {
            viewModel.openLink()
        }

        viewModel.liveSwitchState.observe(viewLifecycleOwner, Observer {
            binding.themeSwitch.isChecked = it
        })

        binding.themeSwitch.setOnCheckedChangeListener { _, checked ->
            viewModel.changeTheme(checked)
        }
    }
}

