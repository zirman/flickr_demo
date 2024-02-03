package com.flickr.demo.common.api

import com.flickr.demo.common.scalars.Url
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("media")
data class MediaApi(
    @SerialName("m")
    val url: Url
)
