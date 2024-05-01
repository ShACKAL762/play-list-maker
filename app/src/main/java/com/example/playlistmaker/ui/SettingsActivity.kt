package com.example.playlistmaker.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.example.playlistmaker.R
import com.example.playlistmaker.data.App

class SettingsActivity : AppCompatActivity() {
    private val prefName = "main_preferences"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val backButton = findViewById<ImageButton>(R.id.arrow_back)
        val shareButton = findViewById<FrameLayout>(R.id.share_button)
        val supportButton = findViewById<FrameLayout>(R.id.support_button)
        val eulaButton = findViewById<FrameLayout>(R.id.eula_button)
        val themeSwitcher = findViewById<SwitchCompat>(R.id.theme_switch)


        shareButton.setOnClickListener {

            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.uri_of_course))
            shareIntent.setType("text/plain")
            startActivity(shareIntent)
        }

        supportButton.setOnClickListener {

            val supportIntent = Intent(Intent.ACTION_SENDTO)
            val uri = Uri.parse("mailto:")
            supportIntent.setData(uri)
            supportIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.mail)))
            supportIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject))
            supportIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.mail_text))
            startActivity(supportIntent)
        }

        eulaButton.setOnClickListener {
            val eluaIntent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.eula_uri)))
            startActivity(eluaIntent)
        }

        backButton.setOnClickListener {
            finish()
        }

        themeSwitcher.setOnCheckedChangeListener { _, checked ->
            (applicationContext as App).switchTheme(checked)
        }

        
        themeSwitcher.isChecked =
            getSharedPreferences(prefName, MODE_PRIVATE).getBoolean("darkTheme",false)
    }

}

