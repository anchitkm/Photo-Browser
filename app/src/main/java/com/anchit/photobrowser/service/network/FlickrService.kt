package com.anchit.photobrowser.service.network

import com.anchit.photobrowser.service.model.FlickrResponse
import retrofit2.Call
import retrofit2.http.Query
import retrofit2.http.GET

interface FlickrService {

    @GET("services/rest/?method=flickr.photos.getRecent&format=json&nojsoncallback=1&extras=url_s")
    fun getRecentPhotos(
        @Query("page") page: Int,
        @Query("per_page") size: Int,
        @Query("api_key") key: String
    ): Call<FlickrResponse>
}