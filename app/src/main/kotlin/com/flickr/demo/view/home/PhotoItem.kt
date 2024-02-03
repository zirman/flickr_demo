package com.flickr.demo.view.home

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.flickr.demo.common.data.PhotoItem
import com.flickr.demo.common.scalars.Url

@Composable
fun PhotoItem(
    photoItem: PhotoItem,
    onClick: (Url) -> Unit,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = photoItem.media.url.string,
        contentDescription = photoItem.description.string,
        contentScale = ContentScale.Crop,
        modifier = modifier.clickable { onClick(photoItem.media.url) },
    )
}
