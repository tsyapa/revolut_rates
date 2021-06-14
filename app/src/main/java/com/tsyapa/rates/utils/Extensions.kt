package com.tsyapa.rates.utils

import android.content.Context
import android.graphics.drawable.PictureDrawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.tsyapa.rates.utils.glide.GlideApp
import com.tsyapa.rates.utils.glide.SvgSoftwareLayerSetter

fun ImageView.loadUrl(url: String){
    GlideApp.with(this).`as`(PictureDrawable::class.java)
        .load(url)
        .listener(SvgSoftwareLayerSetter())
        .into(this)
}

fun showKeyboard(context: Context, view: View) {
    view.requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}