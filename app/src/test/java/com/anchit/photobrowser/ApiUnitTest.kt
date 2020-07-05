package com.anchit.photobrowser

import com.anchit.photobrowser.service.model.FlickrResponse
import com.anchit.photobrowser.service.network.FlickrService
import com.anchit.photobrowser.util.Constants
import okhttp3.OkHttpClient
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 */
class ApiUnitTest {

    @RunWith(JUnit4::class)
    class ApiServiceTest {
        private var apiService: FlickrService? = null

        @Before
        fun createService() {
            val okHttpClient = OkHttpClient.Builder()
            okHttpClient.readTimeout(30, TimeUnit.SECONDS);
            okHttpClient.writeTimeout(30, TimeUnit.SECONDS);
            apiService = Retrofit.Builder()
                .baseUrl("https://api.flickr.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient.build())
                .build().create(FlickrService::class.java)
        }


        @Test
        fun fetchSuccessResult() {
            try {
                val response: Response<*> = apiService!!.getRecentPhotos(1,50,Constants.key).execute()
                assertEquals((response.body() as FlickrResponse).stat, "Pass")
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        /**
         *  Passed wrong api key must return Fail, so checked with Pass
         */
        @Test
        fun fetchFailureResult() {
            try {
                val response: Response<*> = apiService!!.getRecentPhotos(1,50,Constants.secret).execute()
                assertEquals((response.body() as FlickrResponse).stat, "ok")
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        /**
         * Passing test case as passing wrong photo id to fetch photo detail
         */
        @Test
        fun fetchPhotoDetalSuccess(){
            try {
                val response: Response<*> = apiService!!.getPhotoDetails(Constants.key,"50077954373").execute()
                assertEquals((response.body() as FlickrResponse).stat, "ok")
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        /**
         * Failing test case as passing wrong photo id
         */
        @Test
        fun fetchPhotoDetalFailure(){
            try {
                val response: Response<*> = apiService!!.getPhotoDetails(Constants.key,"0").execute()
                assertEquals((response.body() as FlickrResponse).stat, "ok")
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }


    }
}