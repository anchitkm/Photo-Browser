package com.anchit.photobrowser.service.repository

import androidx.lifecycle.MutableLiveData
import com.anchit.photobrowser.service.model.FlickrResponse
import com.anchit.photobrowser.service.network.FlickrService
import com.anchit.photobrowser.service.network.FlickrServiceBuilder
import com.anchit.photobrowser.util.Constants
import retrofit2.Call

object Repository {

    var error: MutableLiveData<Boolean> = MutableLiveData()
    val flickrPhotoList=MutableLiveData<List<FlickrResponse.Photos.Photo>>()
    private fun getApi(): FlickrService {

        return FlickrServiceBuilder.buildService(FlickrService::class.java)
    }


    fun getRecentPhotos(pageNo: Int,pageSize:Int): Call<FlickrResponse> {

        return getApi().getRecentPhotos(pageNo,pageSize,Constants.key)

    }

}