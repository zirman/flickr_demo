package com.flickr.demo.common.api

import com.flickr.demo.common.scalars.Description
import com.flickr.demo.common.scalars.Generator
import com.flickr.demo.common.scalars.Link
import com.flickr.demo.common.scalars.Modified
import com.flickr.demo.common.scalars.Tags
import com.flickr.demo.common.scalars.Title
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val baseUrl = "https://api.flickr.com/"

@Serializable
@SerialName("photos")
data class PhotosApiRequest(
    @SerialName("title")
    val title: Title,
    @SerialName("link")
    val link: Link,
    @SerialName("description")
    val description: Description,
    @SerialName("modified")
    val modified: Modified,
    @SerialName("generator")
    val generator: Generator,
    @SerialName("items")
    val items: List<PhotoItemApi>,
)

suspend fun HttpClient.photosApiRequest(tags: Tags): PhotosApiRequest {
    return get("$baseUrl/services/feeds/photos_public.gne") {
        parameter("format", "json")
        parameter("nojsoncallback", 1)
        parameter("tags", tags.string)
    }.body()
}
