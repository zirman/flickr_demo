package com.flickr.demo.view.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.flickr.demo.common.navigation.MainRoute

fun NavGraphBuilder.homeScreen(navController: NavController) {
    composable(route = MainRoute.Home.path) {
        HomeScaffold(navController = navController, modifier = Modifier.fillMaxSize())
    }
}
