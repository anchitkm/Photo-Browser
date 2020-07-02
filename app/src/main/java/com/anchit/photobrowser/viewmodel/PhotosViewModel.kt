package com.anchit.photobrowser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anchit.photobrowser.service.model.FlickrResponse
import com.anchit.photobrowser.service.repository.Repository

class PhotosViewModel : ViewModel() {

    companion object {
        private val TAG = PhotosViewModel::class.java.simpleName
    }

    val listFlickrPhoto=MutableLiveData<List<FlickrResponse.Photos.Photo>>()
    var error: LiveData<Boolean> = MutableLiveData()
    private var PAGE_NO = 1
    private var PAGE_SIZE = 100



    fun getFlickrPhotos(){

        Repository.flickrPhotoList.observeForever {

        listFlickrPhoto.value=it
        }
            Repository.getRecentPhotos(
                pageNo = PAGE_NO,
                pageSize=PAGE_SIZE
            )
    }

    override fun onCleared() {
        super.onCleared()
        Repository.flickrPhotoList.removeObserver { it }
    }

    fun refresh() {
        getFlickrPhotos()
    }
}