package com.anchit.photobrowser.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anchit.photobrowser.service.model.FlickrResponse

open class MyObserver :ViewModel(){

    val listFlickrPhoto=MutableLiveData<List<FlickrResponse.Photos.Photo>>()

    fun data(mList: List<FlickrResponse.Photos.Photo>) {
        listFlickrPhoto.value = mList
    }
}