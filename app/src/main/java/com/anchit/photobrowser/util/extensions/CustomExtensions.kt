package com.anchit.photobrowser.util.extensions

import android.view.View
import android.widget.ImageView
import com.anchit.photobrowser.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar

fun ImageView.loadImage(uri: String?) {
    val options = RequestOptions()
        .placeholder(R.drawable.placeholder)
        .error(R.drawable.placeholder)
    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}

fun showSnackBar(view: View, message: String){
    Snackbar.make(view,message,Snackbar.LENGTH_LONG).setAction("Close"){}.show()
}