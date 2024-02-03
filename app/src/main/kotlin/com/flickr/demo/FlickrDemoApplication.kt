package com.flickr.demo

import android.app.Application
import coil.Coil
import coil.ImageLoader
import com.flickr.demo.common.data.MemoryCache
import com.flickr.demo.common.data.PhotoItem
import com.flickr.demo.common.scalars.Tags
import dagger.hilt.android.HiltAndroidApp
import io.ktor.client.HttpClient
import javax.inject.Inject

@HiltAndroidApp
class FlickrDemoApplication : Application() {

    @Inject
    lateinit var httpClient: HttpClient

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var tagsMemoryCache: MemoryCache<Tags, List<PhotoItem>>

    @Inject
    lateinit var urlMemoryCache: MemoryCache<Tags, List<PhotoItem>>

    override fun onCreate() {
        super.onCreate()
        Coil.setImageLoader { imageLoader }
    }

    override fun onTerminate() {
        httpClient.close()
        super.onTerminate()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        imageLoader.memoryCache?.clear()
        tagsMemoryCache.clear()
        urlMemoryCache.clear()
    }
}
