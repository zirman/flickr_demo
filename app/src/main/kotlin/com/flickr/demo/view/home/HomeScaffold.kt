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
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import com.flickr.demo.common.navigation.MainRoute
import com.flickr.demo.common.scalars.Tags
import com.flickr.demo.common.view.R
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeScaffold(
    navController: NavController,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    scope: CoroutineScope = rememberCoroutineScope(),
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.windowInsetsPadding(
                    WindowInsets.safeContent.only(WindowInsetsSides.Bottom)
                ),
            ) { snackbarData -> Snackbar(snackbarData) }
        },
        topBar = {
            val searchTags by homeViewModel.searchTagsStateFlow.collectAsStateWithLifecycle()

            val lifecycle = LocalLifecycleOwner.current
            val anErrorOccurred = stringResource(id = R.string.an_error_occurred)
            val retry = stringResource(id = R.string.retry)

            LaunchedEffect(Unit) {
                scope.launch {
                    // show errors in snackbar
                    homeViewModel.errorsFlow
                        .flowWithLifecycle(lifecycle.lifecycle)
                        .collect {
                            val result = snackbarHostState.showSnackbar(
                                message = anErrorOccurred,
                                actionLabel = retry,
                                withDismissAction = true,
                            )

                            if (result == SnackbarResult.ActionPerformed) {
                                homeViewModel.retry()
                            }
                        }
                }
            }

            SearchBar(
                query = searchTags.string,
                onQueryChange = {
                    // clear snackbar of errors
                    snackbarHostState.currentSnackbarData?.dismiss()
                    // update search tags
                    homeViewModel.searchTagsStateFlow.value = Tags(it)
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
                val photoItems by homeViewModel.photosStateFlow
                    .collectAsStateWithLifecycle(initialValue = null)

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

                val loading by homeViewModel.loadingStateFlow.collectAsStateWithLifecycle()
                if (loading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        },
        modifier = modifier,
    )
}
