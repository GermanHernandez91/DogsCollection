package com.example.dogscollection.bindingadapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

class DogBindingAdapters {

    companion object {

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, url: String) {
            imageView.load(url) {
                crossfade(600)
            }
        }
    }
}