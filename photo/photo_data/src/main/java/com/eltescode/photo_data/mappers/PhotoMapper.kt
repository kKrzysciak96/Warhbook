package com.eltescode.photo_data.mappers

import com.eltescode.photo_data.remote.dto.PhotoDto
import com.eltescode.photo_domain.model.Photo

fun PhotoDto.mapToPhoto() = Photo(
    id = id,
    title = title,
    urlS = urlS,
)