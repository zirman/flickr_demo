package com.flickr.demo.view.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.flickr.demo.common.data.PhotoItem
import com.flickr.demo.common.scalars.Url
import kotlinx.collections.immutable.ImmutableList

@Composable
fun HomeContent(
    loading: Boolean,
    photoItems: ImmutableList<PhotoItem>,
    onClickPhotoItem: (Url) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
        ) {
            items(items = photoItems) {
                PhotoItem(
                    photoItem = it,
                    onClick = onClickPhotoItem,
                    modifier = Modifier.aspectRatio(1f),
                )
            }
        }

        if (loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}
