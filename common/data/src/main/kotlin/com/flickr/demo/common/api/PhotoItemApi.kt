package com.flickr.demo.common.api

import com.flickr.demo.common.scalars.Author
import com.flickr.demo.common.scalars.AuthorId
import com.flickr.demo.common.scalars.DateTaken
import com.flickr.demo.common.scalars.Description
import com.flickr.demo.common.scalars.Published
import com.flickr.demo.common.scalars.Tags
import com.flickr.demo.common.scalars.Title
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("item")
data class PhotoItemApi(
    @SerialName("title")
    val title: Title,
    @SerialName("media")
    val media: MediaApi,
    @SerialName("date_taken")
    val dateTaken: DateTaken,
    @SerialName("description")
    val description: Description,
    @SerialName("published")
    val published: Published,
    @SerialName("author")
    val author: Author,
    @SerialName("author_id")
    val authorId: AuthorId,
    @SerialName("tags")
    val tags: Tags,
)
