package com.flickr.demo.view.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    mainNavController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = mainNavController,
        startDestination = mainGraphRoutePattern,
        modifier = modifier,
    ) {
        mainGraph(navController = mainNavController)
    }
}
