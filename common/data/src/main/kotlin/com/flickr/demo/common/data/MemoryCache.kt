package com.flickr.demo.common.data

import kotlinx.collections.immutable.ImmutableMap
import kotlinx.coroutines.flow.StateFlow

interface MemoryCache<T : Any, R : Any> {
    operator fun set(key: T, value: R)
    operator fun get(key: T): R?
    fun getStateFlow(): StateFlow<ImmutableMap<T, R>>
    fun clear()
}
