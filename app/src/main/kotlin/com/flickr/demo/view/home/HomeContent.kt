package com.flickr.demo.view.home

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.flickr.demo.common.data.PhotoItem
import kotlinx.collections.immutable.ImmutableList

@Composable
fun HomeContent(
    photoItems: ImmutableList<PhotoItem>,
    onClickPhotoItem: (PhotoItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        modifier = modifier.fillMaxSize(),
    ) {
        items(items = photoItems) {
            PhotoItem(
                photoItem = it,
                onClick = onClickPhotoItem,
                modifier = Modifier.aspectRatio(1f),
            )
        }
    }
}
