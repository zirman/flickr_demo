package com.flickr.demo.view.detail

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.flickr.demo.common.scalars.Url

@Composable
fun DetailScaffold(
    navController: NavController,
    url: Url?,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    detailViewModel: DetailViewModel = hiltViewModel(),
) {
    val photoItem by detailViewModel.photoStateFlow(url!!)
        .collectAsStateWithLifecycle(initialValue = null)

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
