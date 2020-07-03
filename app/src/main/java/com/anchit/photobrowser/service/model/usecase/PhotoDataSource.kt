package com.anchit.photobrowser.service.model.usecase

import androidx.paging.PageKeyedDataSource
import com.anchit.photobrowser.service.model.FlickrResponse
import com.anchit.photobrowser.service.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotoDataSource(var scope: CoroutineScope) :
    PageKeyedDataSource<Long, FlickrResponse.Photos.Photo>() {

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, FlickrResponse.Photos.Photo>
    ) {
        scope.launch {

           Repository.getRecentPhotos(1,params.requestedLoadSize).enqueue(object : Callback<FlickrResponse> {
                override fun onFailure(call: Call<FlickrResponse>, t: Throwable) {
                    Repository.error.value = true
                }

                override fun onResponse(call: Call<FlickrResponse>, response: Response<FlickrResponse>) {
                    if (response.isSuccessful) {
                        val flickrResponse = response.body()
                        val flickrPhotos = flickrResponse?.photos
                        flickrPhotos?.let {
                            val photoList:List<FlickrResponse.Photos.Photo>?=it.photo
                            photoList?.let {
                                callback.onResult(photoList,null,2)
                            }
                        }
//                        Repository.flickrPhotoList.value = flickrPhotos?.photo
                    }else {
                        Repository.error.value=true
                    }

                }
            })

        }
    }

    override fun loadAfter(
        params: LoadParams<Long>,
        callback: LoadCallback<Long, FlickrResponse.Photos.Photo>
    ) {
       scope.launch {
           Repository.getRecentPhotos(params.key.toInt(),params.requestedLoadSize)
               .enqueue(object :Callback<FlickrResponse>{
                   override fun onFailure(call: Call<FlickrResponse>, t: Throwable) {
                       Repository.error.value = true
                   }

                   override fun onResponse(
                       call: Call<FlickrResponse>,
                       response: Response<FlickrResponse>
                   ) {
                       val flickrResponse=response.body()
                       flickrResponse?.let {
                           val photoList:List<FlickrResponse.Photos.Photo> ?=flickrResponse.photos.photo
                           photoList?.let {
                               callback.onResult(it,params.key+1)
                           }
                       }
                   }

               })
       }
    }

    override fun loadBefore(
        params: LoadParams<Long>,
        callback: LoadCallback<Long, FlickrResponse.Photos.Photo>
    ) {
        TODO("Not yet implemented")
    }

}