package com.gousto.philip.arnold.utils

import android.util.DisplayMetrics
import android.view.Window

fun Window.getDisplayWidth(): Int {
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.widthPixels
}