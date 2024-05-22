package com.example.playlistmaker.ui.settings.activity


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.databinding.ActivitySettingsBinding
import com.example.playlistmaker.ui.settings.view_model.SettingViewModel
import com.example.playlistmaker.ui.settings.view_model.SettingViewModelFactory

class SettingsActivity : AppCompatActivity() {

    private lateinit var viewModel: SettingViewModel
    private lateinit var binding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        viewModel =
            ViewModelProvider(
                this,
                SettingViewModelFactory(this)
            )[SettingViewModel::class.java]

        viewModel.getTheme()

        binding.shareButton.setOnClickListener {
            viewModel.share()
        }

        binding.supportButton.setOnClickListener {
            viewModel.sendSupport()
        }

        binding.eulaButton.setOnClickListener {
            viewModel.openLink()
        }

        binding.arrowBack.setOnClickListener {
            finish()
        }

        viewModel.liveSwitchState.observe(this, Observer {
            binding.themeSwitch.isChecked = it
        })

        binding.themeSwitch.setOnCheckedChangeListener { _, checked ->
            viewModel.changeTheme(checked)
        }
    }
}

