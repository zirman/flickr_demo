package com.flickr.demo.view.home

import com.flickr.demo.common.data.PhotoItem
import com.flickr.demo.common.scalars.Tags

data class HomeUiState(
    val searchTags: Tags = Tags(""),
    val loading: Boolean = false,
    val photos: List<PhotoItem>? = null,
)
