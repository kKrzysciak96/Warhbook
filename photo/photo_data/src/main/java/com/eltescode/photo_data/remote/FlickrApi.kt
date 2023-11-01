package com.eltescode.photo_data.remote

import com.eltescode.core_data.api.Your_Api_Key
import com.eltescode.photo_data.remote.dto.PhotosResponse

import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {

    @GET("services/rest?method=flickr.photos.search")
    suspend fun searchPhotos(@Query("text") query: String): PhotosResponse

    companion object {
        const val BASE_URL = "https://api.flickr.com/"
        const val API_KEY = Your_Api_Key
    }
}