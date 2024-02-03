package com.flickr.demo.common.data

import com.flickr.demo.common.api.photosApiRequest
import com.flickr.demo.common.scalars.Tags
import com.flickr.demo.common.scalars.Url
import io.ktor.client.HttpClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotosRepository @Inject constructor(
    private val apiDataSource: HttpClient,
    private val tagsMemoryCacheDataSource: MemoryCache<Tags, List<PhotoItem>>,
    private val urlMemoryCacheDataSource: MemoryCache<Url, PhotoItem>,
) {
    suspend fun getPhotosByTags(tags: Tags): List<PhotoItem> {
        val cachedData = tagsMemoryCacheDataSource[tags]

        if (cachedData != null) {
            return cachedData
        }

        val apiData = apiDataSource.photosApiRequest(tags)
            .items
            .map { it.toPhotoItem() }

        // insert api items in data sources
        tagsMemoryCacheDataSource[tags] = apiData

        apiData.forEach { photoItem ->
            urlMemoryCacheDataSource[photoItem.media.url] = photoItem
        }

        return apiData
    }

    fun getPhotoByUrl(url: Url): PhotoItem? {
        return urlMemoryCacheDataSource[url]
    }
}
