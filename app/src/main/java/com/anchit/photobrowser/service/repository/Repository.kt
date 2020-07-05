package com.anchit.photobrowser.service.repository

import androidx.lifecycle.MutableLiveData
import com.anchit.photobrowser.BuildConfig
import com.anchit.photobrowser.service.model.FlickrResponse
import com.anchit.photobrowser.service.model.PhotoInfo
import com.anchit.photobrowser.service.network.FlickrService
import com.anchit.photobrowser.service.network.FlickrServiceBuilder
import com.anchit.photobrowser.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object Repository {

    var error: MutableLiveData<Boolean> = MutableLiveData()
    var photoInfo = MutableLiveData<PhotoInfo>()
    private fun getApi(): FlickrService {

        return FlickrServiceBuilder.buildService(FlickrService::class.java)
    }


    fun getRecentPhotos(pageNo: Int, pageSize: Int): Call<FlickrResponse> {

        return getApi().getRecentPhotos(pageNo, pageSize, BuildConfig.CONSUMER_KEY)

    }

    /**
     * This is to get the photo details on the basis of provided photoID.
     *
     * @param photoId - Photoid of which the details will be fetched.
     */
    fun getPhotoDetails(photoId: String) {
        val response = getApi().getPhotoDetails(BuildConfig.CONSUMER_KEY, photoId)


        response.enqueue(object : Callback<PhotoInfo> {
            override fun onFailure(call: Call<PhotoInfo>, t: Throwable) {
                error.value = true
            }

            override fun onResponse(call: Call<PhotoInfo>, response: Response<PhotoInfo>) {
                if (response.isSuccessful) {
                    val photoDetailResponse = response.body()
                    photoInfo.value = photoDetailResponse

                } else {
                    error.value = true
                }

            }
        })
    }
}