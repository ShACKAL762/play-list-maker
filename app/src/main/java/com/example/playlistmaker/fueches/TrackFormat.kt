package com.example.playlistmaker.fueches

import android.content.Context
import com.example.playlistmaker.R

fun trackFormat(size: Int, context: Context): String {
    return context.resources.getQuantityString(R.plurals.plurals_track,size) }
fun minuteFormat(size: Int,context: Context): String{
    return context.resources.getQuantityString(R.plurals.plurals_minute,size)
}