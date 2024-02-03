package com.flickr.demo.view.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.flickr.demo.common.data.PhotoItem

@Composable
fun PhotoItemDetail(
    photoItem: PhotoItem,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        AsyncImage(
            model = photoItem.media.url.string,
            contentDescription = photoItem.description.string,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth(),
        )

        Text(text = photoItem.title.string)
        Text(text = photoItem.description.string) // TODO format
        Text(text = photoItem.author.string)
        Text(text = photoItem.published.string) // TODO format
    }
}
