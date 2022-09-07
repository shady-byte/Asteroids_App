package com.example.asteroidapp.dataBase

import com.squareup.moshi.Json

data class NasaTodayImage(
    val url: String,
    @Json(name = "media_type") val imageType: String,
    val title: String
) {
    val checkIsImage
        get() = imageType == "image"
}
