package com.flickr.demo.common.data

import android.os.Parcelable
import com.flickr.demo.common.api.MediaApi
import com.flickr.demo.common.scalars.Url
import kotlinx.parcelize.Parcelize

@Parcelize
data class Media(
    val url: Url,
) : Parcelable

fun MediaApi.toMedia(): Media {
    return Media(
        url = url,
    )
}
