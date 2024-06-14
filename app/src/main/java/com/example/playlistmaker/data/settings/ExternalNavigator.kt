package com.example.playlistmaker.data.settings

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.getString
import com.example.playlistmaker.R
import com.example.playlistmaker.domain.settings.repository.SharingRepoitory

class ExternalNavigator(private val context: Context) : SharingRepoitory {

    override fun shareApp() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.putExtra(Intent.EXTRA_TEXT, getString(context, R.string.uri_of_course))
        shareIntent.setType("text/plain")
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(shareIntent)
    }

    override fun openLink() {
        val eluaIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse(getString(context, R.string.eula_uri)))
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(eluaIntent)
    }

    override fun openSupport() {
        val supportIntent = Intent(Intent.ACTION_SENDTO)
        val uri = Uri.parse("mailto:")
        supportIntent.setData(uri)
        supportIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(context, R.string.mail)))
        supportIntent.putExtra(Intent.EXTRA_SUBJECT, getString(context, R.string.subject))
        supportIntent.putExtra(Intent.EXTRA_TEXT, getString(context, R.string.mail_text))
        supportIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(supportIntent)
    }
}