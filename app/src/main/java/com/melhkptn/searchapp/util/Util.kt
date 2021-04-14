package com.melhkptn.searchapp.util

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.View
import android.view.inputmethod.InputMethod.SHOW_FORCED
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.melhkptn.searchapp.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun ImageView.downloadImage(url: String, context: Context) {

    val options = RequestOptions().placeholder(createPlaceHolder(context))
        .error(R.drawable.ic_launcher_foreground)

    Glide
        .with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}

fun String.convertDate(): LocalDate =
    LocalDate.parse(this, DateTimeFormatter.ISO_DATE_TIME)


private fun createPlaceHolder(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.showKeyboard() {
    (context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
        .toggleSoftInput(SHOW_FORCED, 0)
}

fun View.hideKeyboard() {
    (context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow(windowToken, 0)
}