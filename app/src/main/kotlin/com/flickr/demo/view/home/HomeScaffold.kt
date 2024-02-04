package com.flickr.demo.view.home

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.flickr.demo.common.navigation.MainRoute
import com.flickr.demo.common.scalars.Tags
import kotlinx.collections.immutable.toImmutableList

@Composable
fun HomeScaffold(
    navController: NavController,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        snackbarHost = {
            HomeSnackbarHost(
                snackbarHostState = snackbarHostState,
                errorsFlow = homeViewModel.errorsFlow,
                onRetry = homeViewModel::retry,
                modifier = Modifier.windowInsetsPadding(
                    WindowInsets.safeContent.only(WindowInsetsSides.Bottom)
                ),
            )
        },
        topBar = {
            HomeTopBar(
                searchTags = uiState.searchTags,
                onQueryChange = {
                    // clear snackbar of errors
                    snackbarHostState.currentSnackbarData?.dismiss()
                    // update search tags
                    homeViewModel.searchTagsChange(Tags(it))
                },
            )
        },
        content = { paddingValues ->
            HomeContent(
                loading = uiState.loading,
                photoItems = uiState.photos.orEmpty().toImmutableList(),
                onClickPhotoItem = { url ->
                    navController.navigate(
                        route = MainRoute.Detail.getUriBuilder()
                            .appendQueryParameter("url", url.string)
                            .toString(),
                    )
                },
                modifier = Modifier.padding(paddingValues),
            )
        },
        modifier = modifier,
    )
}
