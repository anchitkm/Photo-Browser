package com.anchit.photobrowser.service.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class FlickrResponse(
    @SerializedName("photos")
    val photos: Photos = Photos(),
    @SerializedName("stat")
    val stat: String = "" // ok
) {
    @Keep
    data class Photos(
        @SerializedName("page")
        val page: Int = 0, // 1
        @SerializedName("pages")
        val pages: Int = 0, // 10
        @SerializedName("perpage")
        val perpage: Int = 0, // 100
        @SerializedName("photo")
        val photo: List<Photo> = listOf(),
        @SerializedName("total")
        val total: Int = 0 // 1000
    ) {
        @Keep
        data class Photo(
            @SerializedName("farm")
            val farm: Int = 0, // 66
            @SerializedName("height_s")
            val heightS: Int = 0, // 112
            @SerializedName("id")
            val id: String = "", // 50063173028
            @SerializedName("isfamily")
            val isfamily: Int = 0, // 0
            @SerializedName("isfriend")
            val isfriend: Int = 0, // 0
            @SerializedName("ispublic")
            val ispublic: Int = 0, // 1
            @SerializedName("owner")
            val owner: String = "", // 7756404@N07
            @SerializedName("secret")
            val secret: String = "", // 69ae51483e
            @SerializedName("server")
            val server: String = "", // 65535
            @SerializedName("title")
            val title: String = "", // Panorama 4000 hdr pregamma 1 fattal alpha 1 beta 0 by bruhinb on DeviantArt
            @SerializedName("url_s")
            val urlS: String = "", // https://live.staticflickr.com/65535/50063173028_69ae51483e_m.jpg
            @SerializedName("width_s")
            val widthS: Int = 0 // 240
        )
    }
}