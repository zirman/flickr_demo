package com.flickr.demo.view.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.flickr.demo.common.data.PhotoItem

@Composable
fun DetailContent(
    photoItem: PhotoItem?,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        photoItem
            ?.also { photoItem ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                ) {
                    PhotoItemDetail(photoItem = photoItem)
                }
            }
            ?: CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}
