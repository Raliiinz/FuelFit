package com.example.fuelfit.utils

fun String.stripHtml(): String {
    return android.text.Html.fromHtml(this, android.text.Html.FROM_HTML_MODE_LEGACY)
        .toString()
        .trim()
}
