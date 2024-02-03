package com.flickr.demo.view.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.flickr.demo.common.navigation.MainRoute
import com.flickr.demo.common.scalars.toUrl

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
        DetailScaffold(
            navController = navController,
            url = navBackStackEntry.arguments?.getString("url")?.toUrl(),
            modifier = Modifier.fillMaxSize(),
        )
    }
}
