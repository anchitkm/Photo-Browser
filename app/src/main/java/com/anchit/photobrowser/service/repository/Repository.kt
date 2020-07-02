package com.anchit.photobrowser.service.repository

import androidx.lifecycle.MutableLiveData
import com.anchit.photobrowser.service.model.FlickrResponse
import com.anchit.photobrowser.service.network.FlickrService
import com.anchit.photobrowser.service.network.FlickrServiceBuilder
import com.anchit.photobrowser.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object Repository {

    var error: MutableLiveData<Boolean> = MutableLiveData()
    val flickrPhotoList=MutableLiveData<List<FlickrResponse.Photos.Photo>>()
    private fun getApi(): FlickrService {

        return FlickrServiceBuilder.buildService(FlickrService::class.java)
    }

    fun getRecentPhotos(pageNo: Int,pageSize:Int) {
        val response= getApi().getRecentPhotos(pageNo,100,Constants.key)


        response.enqueue(object : Callback<FlickrResponse> {
            override fun onFailure(call: Call<FlickrResponse>, t: Throwable) {
                error.value = true
            }

            override fun onResponse(call: Call<FlickrResponse>, response: Response<FlickrResponse>) {
                if (response.isSuccessful) {
                    val flickrResponse = response.body()
                    val flickrPhotos = flickrResponse?.photos
                    flickrPhotoList.value = flickrPhotos?.photo
                }else {
                    error.value=true
                }

            }
        })
    }
}