package com.flickr.demo

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApplicationLifecycleOwner

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @ApplicationLifecycleOwner
    @Singleton
    @Provides
    fun provideApplicationLifecycleOwner(): LifecycleOwner = ProcessLifecycleOwner.get()

    @Singleton
    @Provides
    fun provideImageLoader(@ApplicationContext context: Context): ImageLoader {
        return ImageLoader
            .Builder(context)
            .memoryCache {
                MemoryCache.Builder(context)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.02)
                    .build()
            }
            .build()
    }
}
