package com.example.playlistmaker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val backButton = findViewById<ImageButton>(R.id.arrow_back)
        val shareButton = findViewById<FrameLayout>(R.id.share_button)
        val supportButton = findViewById<FrameLayout>(R.id.support_button)
        val eulaButton = findViewById<FrameLayout>(R.id.eula_button)



        shareButton.setOnClickListener{

            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.uri_of_course))
            shareIntent.setType("text/plain");
            startActivity(shareIntent)
        }

        supportButton.setOnClickListener{

            val supportIntent = Intent(Intent.ACTION_SENDTO)
            val uri = Uri.parse("mailto:")
            supportIntent.setData(uri)
            supportIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.mail)))
            supportIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject))
            supportIntent.putExtra(Intent.EXTRA_TEXT,getString(R.string.mail_text))
            startActivity(supportIntent)
        }

        eulaButton.setOnClickListener{

            val eluaIntent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.eula_uri)))
            startActivity(eluaIntent)
        }

        backButton.setOnClickListener{
            finish()
        }
    }
}

