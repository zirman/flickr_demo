package com.flickr.demo.common.navigation

import android.net.Uri

enum class MainRoute(val path: String) {
    Home("home"),
    Detail("detail");

    fun getUriBuilder(): Uri.Builder = Uri.parse(path).buildUpon()
}
