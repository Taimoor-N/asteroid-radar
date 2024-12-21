package com.udacity.asteroidradar.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class PictureOfDay
    (
    val date: String,
    val explanation: String,
    @Json(name = "hdurl") val hdUrl: String,
    @Json(name = "media_type") val mediaType: String,
    @Json(name = "service_version") val serviceVersion: String,
    val title: String,
    val url: String
) : Parcelable