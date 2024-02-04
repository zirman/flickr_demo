package com.flickr.demo.view.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.flickr.demo.common.navigation.MainRoute
import com.flickr.demo.common.scalars.Tags
import com.flickr.demo.common.view.R
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
                onRetry = { homeViewModel.retry() },
                modifier = Modifier.windowInsetsPadding(
                    WindowInsets.safeContent.only(WindowInsetsSides.Bottom)
                ),
            )
        },
        topBar = {
            SearchBar(
                query = uiState.searchTags.string,
                onQueryChange = {
                    // clear snackbar of errors
                    snackbarHostState.currentSnackbarData?.dismiss()
                    // update search tags
                    homeViewModel.searchTagsChange(Tags(it))
                },
                onSearch = {
                },
                active = false,
                onActiveChange = { },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.TwoTone.Search,
                        contentDescription = stringResource(id = R.string.search),
                    )
                },
                modifier = Modifier.padding(4.dp),
            ) { }
        },
        content = { paddingValues ->
            Box {
                val photoItems = uiState.photos

                HomeContent(
                    photoItems = photoItems.orEmpty().toImmutableList(),
                    onClickPhotoItem = { url ->
                        navController.navigate(
                            route = MainRoute.Detail.getUriBuilder()
                                .appendQueryParameter("url", url.string)
                                .toString(),
                        )
                    },
                    modifier = Modifier.padding(paddingValues),
                )

                if (uiState.loading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        },
        modifier = modifier,
    )
}
