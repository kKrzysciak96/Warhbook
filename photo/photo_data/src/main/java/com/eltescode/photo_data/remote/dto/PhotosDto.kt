package com.eltescode.photo_data.remote.dto

import com.squareup.moshi.Json

data class PhotosResponse(
    @field:Json(name = "photos") val photos: PhotosDto,
    @field:Json(name = "stat") val stat: String
)

data class PhotosDto(
    @field:Json(name = "page") val page: Int,
    @field:Json(name = "pages") val pages: Int,
    @field:Json(name = "perpage") val perpage: Int,
    @field:Json(name = "photo") val photoList: List<PhotoDto>,
    @field:Json(name = "total") val total: Int
)

data class PhotoDto(
    @field:Json(name = "farm") val farm: Int?,
    @field:Json(name = "height_s") val heightS: Int?,
    @field:Json(name = "id") val id: String?,
    @field:Json(name = "isfamily") val isfamily: Int?,
    @field:Json(name = "isfriend") val isfriend: Int?,
    @field:Json(name = "ispublic") val ispublic: Int?,
    @field:Json(name = "owner") val owner: String?,
    @field:Json(name = "secret") val secret: String?,
    @field:Json(name = "server") val server: String?,
    @field:Json(name = "title") val title: String?,
    @field:Json(name = "url_s") val urlS: String?,
    @field:Json(name = "width_s") val widthS: Int?
)
