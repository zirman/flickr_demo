package com.flickr.demo.view.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.flickr.demo.common.navigation.MainRoute
import com.flickr.demo.common.scalars.Url

fun NavGraphBuilder.detailScreen(navController: NavController) {
    composable(
        route = "${MainRoute.Detail.path}?url={url}",
        arguments = listOf(
            navArgument("url") {
                type = NavType.StringType
                nullable = false
            }
        ),
    ) { navBackStackEntry ->
        val url = navBackStackEntry.arguments
            ?.getString("url")
            ?.let { Url(it) }
            ?: run {
                LaunchedEffect(Unit) { navController.popBackStack() }
                return@composable
            }

        DetailScaffold(
            navController = navController,
            url = url,
            modifier = Modifier.fillMaxSize(),
        )
    }
}
