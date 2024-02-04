package com.flickr.demo.view.detail

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.flickr.demo.common.scalars.Url

@Composable
fun DetailScaffold(
    navController: NavController,
    url: Url?,
    modifier: Modifier = Modifier,
    detailViewModel: DetailViewModel = hiltViewModel(),
) {
    val photoItem = detailViewModel.photoStateFlow(url!!)

    Scaffold(
        content = { paddingValues ->
            DetailContent(
                photoItem = photoItem,
                modifier = Modifier.padding(paddingValues),
            )
        },
        modifier = modifier,
    )
}
