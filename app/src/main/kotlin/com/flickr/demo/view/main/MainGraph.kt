package com.flickr.demo.view.main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.flickr.demo.common.navigation.MainRoute
import com.flickr.demo.view.detail.detailScreen
import com.flickr.demo.view.home.homeScreen

const val mainGraphRoutePattern = "main"

fun NavGraphBuilder.mainGraph(navController: NavController) {
    navigation(
        startDestination = MainRoute.Home.path,
        route = mainGraphRoutePattern,
    ) {
        homeScreen(navController)
        detailScreen(navController)
    }
}
