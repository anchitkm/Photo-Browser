package com.anchit.photobrowser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.anchit.photobrowser.service.model.FlickrResponse
import com.anchit.photobrowser.service.model.usecase.PhotosDataSourceFactory
import com.anchit.photobrowser.util.Constants

class PhotosViewModel : ViewModel() {

    companion object {
        private val TAG = PhotosViewModel::class.java.simpleName
    }

    var showError= MutableLiveData<Boolean>()
    private lateinit var dataSourceFactory: PhotosDataSourceFactory
    lateinit var pagedPhotoList: LiveData<PagedList<FlickrResponse.Photos.Photo>>


    fun initDataSource() {

        dataSourceFactory= PhotosDataSourceFactory(viewModelScope)

        val config = PagedList.Config.Builder()
            .setPageSize(Constants.pageSize)
            .setInitialLoadSizeHint(Constants.pageSize)
            .setEnablePlaceholders(true)
            .build()

        pagedPhotoList = LivePagedListBuilder(dataSourceFactory,config)
            .setBoundaryCallback(object : PagedList.BoundaryCallback<FlickrResponse.Photos.Photo> (){
                override fun onZeroItemsLoaded() {
                    super.onZeroItemsLoaded()
                    showError.postValue(true)
                }

                override fun onItemAtEndLoaded(itemAtEnd: FlickrResponse.Photos.Photo) {
                    super.onItemAtEndLoaded(itemAtEnd)
                    showError.postValue(false)
                }

                override fun onItemAtFrontLoaded(itemAtFront: FlickrResponse.Photos.Photo) {
                    super.onItemAtFrontLoaded(itemAtFront)
                    showError.postValue(false)
                }
            }).build()
    }

}