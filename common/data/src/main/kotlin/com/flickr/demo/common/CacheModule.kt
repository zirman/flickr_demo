package com.flickr.demo.common

import com.flickr.demo.common.data.MemoryCache
import com.flickr.demo.common.data.MemoryCacheImpl
import com.flickr.demo.common.data.PhotoItem
import com.flickr.demo.common.scalars.Tags
import com.flickr.demo.common.scalars.Url
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideTagsMemoryCache(): MemoryCache<Tags, List<PhotoItem>> {
        return MemoryCacheImpl()
    }

    @Singleton
    @Provides
    fun provideUrlMemoryCache(): MemoryCache<Url, PhotoItem> {
        return MemoryCacheImpl()
    }
}
