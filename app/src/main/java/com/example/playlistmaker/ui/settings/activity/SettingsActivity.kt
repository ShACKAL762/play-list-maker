package com.example.playlistmaker.ui.settings.activity


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.playlistmaker.databinding.ActivitySettingsBinding
import com.example.playlistmaker.ui.settings.view_model.SettingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SettingsActivity : AppCompatActivity() {

    private val viewModel: SettingViewModel by viewModel{ parametersOf(this) }
    private lateinit var binding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)

        setContentView(binding.root)

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

