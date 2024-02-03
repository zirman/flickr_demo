package com.flickr.demo.common.data

import android.os.Parcelable
import com.flickr.demo.common.api.PhotoItemApi
import com.flickr.demo.common.scalars.Author
import com.flickr.demo.common.scalars.AuthorId
import com.flickr.demo.common.scalars.DateTaken
import com.flickr.demo.common.scalars.Description
import com.flickr.demo.common.scalars.Published
import com.flickr.demo.common.scalars.Tags
import com.flickr.demo.common.scalars.Title
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoItem(
    val title: Title,
    val media: Media,
    val dateTaken: DateTaken,
    val description: Description,
    val published: Published,
    val author: Author,
    val authorId: AuthorId,
    val tags: Tags,
) : Parcelable

fun PhotoItemApi.toPhotoItem(): PhotoItem {
    return PhotoItem(
        title = title,
        media = media.toMedia(),
        dateTaken = dateTaken,
        description = description,
        published = published,
        author = author,
        authorId = authorId,
        tags = tags,
    )
}
