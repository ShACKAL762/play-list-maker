package com.example.playlistmaker.ui.settings.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.R
import com.example.playlistmaker.App
import com.example.playlistmaker.ui.settings.view_model.SettingViewModel
import com.example.playlistmaker.ui.settings.view_model.SettingViewModelFactory

class SettingsActivity : AppCompatActivity() {
    private val prefName = "main_preferences"
    private lateinit var viewModel: SettingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        println("AAA")
        viewModel =
            ViewModelProvider(
                this,
                SettingViewModelFactory(applicationContext)
            )[SettingViewModel::class.java]

        val backButton = findViewById<ImageButton>(R.id.arrow_back)
        val shareButton = findViewById<FrameLayout>(R.id.share_button)
        val supportButton = findViewById<FrameLayout>(R.id.support_button)
        val eulaButton = findViewById<FrameLayout>(R.id.eula_button)
        val themeSwitcher = findViewById<SwitchCompat>(R.id.theme_switch)


        shareButton.setOnClickListener {
            Log.e("aaa","aaa")
            viewModel.share()
        }

        supportButton.setOnClickListener {
            viewModel.sendSupport()
        }

        eulaButton.setOnClickListener {
            viewModel.openLink()
        }

        backButton.setOnClickListener {
            finish()
        }

        themeSwitcher.setOnCheckedChangeListener { _, checked ->
            //todo 4
            (applicationContext as App).switchTheme(checked)
        }


        themeSwitcher.isChecked =
                //todo 6
            getSharedPreferences(prefName, MODE_PRIVATE).getBoolean("darkTheme", false)
    }

}

