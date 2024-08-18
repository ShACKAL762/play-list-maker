package com.example.playlistmaker.fueches

import android.content.Context
import com.example.playlistmaker.R

fun trackFormat(size: Int, context: Context): String {

    return when (if (size in 11..14) size % 100 else size % 10) {
        1 -> context.getString(R.string.track1)
        2, 3, 4 -> context.getString(R.string.track2)
        else -> context.getString(R.string.track3)}
}