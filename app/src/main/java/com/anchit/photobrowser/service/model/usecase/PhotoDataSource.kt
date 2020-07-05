package com.anchit.photobrowser.service.model.usecase

import androidx.paging.PageKeyedDataSource
import com.anchit.photobrowser.service.model.FlickrResponse
import com.anchit.photobrowser.service.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotoDataSource(private var scope: CoroutineScope) :
    PageKeyedDataSource<Long, FlickrResponse.Photos.Photo>() {

    /**
     * Load initial data.
     * <p>
     * This method is called first to initialize a PagedList with data. If it's possible to count
     * the items that can be loaded by the DataSource, it's recommended to pass the loaded data to
     * the callback via the three-parameter
     * {@link LoadInitialCallback#onResult(List, int, int, Object, Object)}. This enables PagedLists
     * presenting data from this source to display placeholders to represent unloaded items.
     * <p>
     * {@link LoadInitialParams#requestedLoadSize} is a hint, not a requirement, so it may be may be
     * altered or ignored.
     *
     * @param params Parameters for initial load, including requested load size.
     * @param callback Callback that receives initial load data.
     */
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