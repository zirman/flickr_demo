package com.flickr.demo.view.home

import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.flowWithLifecycle
import com.flickr.demo.common.view.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun HomeSnackbarHost(
    snackbarHostState: SnackbarHostState,
    errorsFlow: Flow<Unit>,
    onRetry: suspend () -> Unit,
    modifier: Modifier = Modifier,
    scope: CoroutineScope = rememberCoroutineScope(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val anErrorOccurred = stringResource(id = R.string.an_error_occurred)
    val retry = stringResource(id = R.string.retry)

    LaunchedEffect(Unit) {
        scope.launch {
            // show errors in snackbar
            errorsFlow
                .flowWithLifecycle(lifecycleOwner.lifecycle)
                .collect {
                    val result = snackbarHostState.showSnackbar(
                        message = anErrorOccurred,
                        actionLabel = retry,
                        withDismissAction = true,
                    )

                    if (result == SnackbarResult.ActionPerformed) {
                        onRetry()
                    }
                }
        }
    }

    SnackbarHost(
        hostState = snackbarHostState,
        modifier = modifier,
    ) { snackbarData -> Snackbar(snackbarData) }
}
