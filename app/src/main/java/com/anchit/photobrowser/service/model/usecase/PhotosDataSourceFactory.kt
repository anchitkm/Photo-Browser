package com.anchit.photobrowser.service.model.usecase

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.anchit.photobrowser.service.model.FlickrResponse
import kotlinx.coroutines.CoroutineScope

class PhotosDataSourceFactory(private var scope: CoroutineScope) :
    DataSource.Factory<Long, FlickrResponse.Photos.Photo>() {

    private var dataSourceLiveData = MutableLiveData<PhotoDataSource>()
    override fun create(): DataSource<Long, FlickrResponse.Photos.Photo>{

        val photoDataSource = PhotoDataSource(scope)
        dataSourceLiveData.postValue(photoDataSource)
        return photoDataSource

    }

}