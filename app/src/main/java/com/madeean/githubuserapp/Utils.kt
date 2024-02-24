package com.madeean.githubuserapp

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide

object Utils {
  const val INTENT_DATA = "intent_data"

  fun View.setVisibility(isVisible: Boolean) {
    if (isVisible) {
      this.visibility = View.VISIBLE
    } else {
      this.visibility = View.GONE
    }
  }

  fun <T> ArrayList<T>?.orEmpty(): ArrayList<T> = this ?: arrayListOf()

  fun ImageView.loadUseGifThumb(imageUrl: String, @DrawableRes resGif: Int) {
    Glide.with(this)
      .load(imageUrl)
      .thumbnail(Glide.with(this).load(resGif))
      .into(this)
  }
}