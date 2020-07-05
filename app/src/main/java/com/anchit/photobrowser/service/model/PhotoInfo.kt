package com.anchit.photobrowser.service.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class PhotoInfo(
    @SerializedName("photo")
    val photo: Photo = Photo(),
    @SerializedName("stat")
    val stat: String = "" // ok
) {
    @Keep
    data class Photo(
        @SerializedName("comments")
        val comments: Comments = Comments(),
        @SerializedName("dates")
        val dates: Dates = Dates(),
        @SerializedName("dateuploaded")
        val dateuploaded: String = "", // 1593941604
        @SerializedName("description")
        val description: Description = Description(),
        @SerializedName("editability")
        val editability: Editability = Editability(),
        @SerializedName("farm")
        val farm: Int = 0, // 66
        @SerializedName("id")
        val id: String = "", // 50077754763
        @SerializedName("isfavorite")
        val isfavorite: Int = 0, // 0
        @SerializedName("license")
        val license: String = "", // 0
        @SerializedName("media")
        val media: String = "", // photo
        @SerializedName("notes")
        val notes: Notes = Notes(),
        @SerializedName("owner")
        val owner: Owner = Owner(),
        @SerializedName("people")
        val people: People = People(),
        @SerializedName("publiceditability")
        val publiceditability: Publiceditability = Publiceditability(),
        @SerializedName("rotation")
        val rotation: Int = 0, // 0
        @SerializedName("safety_level")
        val safetyLevel: String = "", // 0
        @SerializedName("secret")
        val secret: String = "", // c730778504
        @SerializedName("server")
        val server: String = "", // 65535
        @SerializedName("tags")
        val tags: Tags = Tags(),
        @SerializedName("title")
        val title: Title = Title(),
        @SerializedName("urls")
        val urls: Urls = Urls(),
        @SerializedName("usage")
        val usage: Usage = Usage(),
        @SerializedName("views")
        val views: String = "", // 0
        @SerializedName("visibility")
        val visibility: Visibility = Visibility()
    ) {
        @Keep
        data class Comments(
            @SerializedName("_content")
            val content: String = "" // 0
        )

        @Keep
        data class Dates(
            @SerializedName("lastupdate")
            val lastupdate: String = "", // 1593941604
            @SerializedName("posted")
            val posted: String = "", // 1593941604
            @SerializedName("taken")
            val taken: String = "", // 2020-07-05 19:03:24
            @SerializedName("takengranularity")
            val takengranularity: Int = 0, // 0
            @SerializedName("takenunknown")
            val takenunknown: String = "" // 1
        )

        @Keep
        data class Description(
            @SerializedName("_content")
            val content: String = ""
        )

        @Keep
        data class Editability(
            @SerializedName("canaddmeta")
            val canaddmeta: Int = 0, // 0
            @SerializedName("cancomment")
            val cancomment: Int = 0 // 0
        )

        @Keep
        data class Notes(
            @SerializedName("note")
            val note: List<Any> = listOf()
        )

        @Keep
        data class Owner(
            @SerializedName("iconfarm")
            val iconfarm: Int = 0, // 5
            @SerializedName("iconserver")
            val iconserver: String = "", // 4066
            @SerializedName("location")
            val location: String = "", // Adelaide Plain, Australia
            @SerializedName("nsid")
            val nsid: String = "", // 30682253@N00
            @SerializedName("path_alias")
            val pathAlias: String = "", // justjjoke
            @SerializedName("realname")
            val realname: String = "", // Just  Judith
            @SerializedName("username")
            val username: String = "" // justjjoke
        )

        @Keep
        data class People(
            @SerializedName("haspeople")
            val haspeople: Int = 0 // 0
        )

        @Keep
        data class Publiceditability(
            @SerializedName("canaddmeta")
            val canaddmeta: Int = 0, // 0
            @SerializedName("cancomment")
            val cancomment: Int = 0 // 1
        )

        @Keep
        data class Tags(
            @SerializedName("tag")
            val tag: List<Any> = listOf()
        )

        @Keep
        data class Title(
            @SerializedName("_content")
            val content: String = "" // Reading before bed :)
        )

        @Keep
        data class Urls(
            @SerializedName("url")
            val url: List<Url> = listOf()
        ) {
            @Keep
            data class Url(
                @SerializedName("_content")
                val content: String = "", // https://www.flickr.com/photos/justjjoke/50077754763/
                @SerializedName("type")
                val type: String = "" // photopage
            )
        }

        @Keep
        data class Usage(
            @SerializedName("canblog")
            val canblog: Int = 0, // 0
            @SerializedName("candownload")
            val candownload: Int = 0, // 0
            @SerializedName("canprint")
            val canprint: Int = 0, // 0
            @SerializedName("canshare")
            val canshare: Int = 0 // 0
        )

        @Keep
        data class Visibility(
            @SerializedName("isfamily")
            val isfamily: Int = 0, // 0
            @SerializedName("isfriend")
            val isfriend: Int = 0, // 0
            @SerializedName("ispublic")
            val ispublic: Int = 0 // 1
        )
    }
}