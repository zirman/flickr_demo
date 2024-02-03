package com.flickr.demo.common.data

import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MemoryCacheImpl<T : Any, R : Any> : MemoryCache<T, R> {
    private val cache: MutableStateFlow<PersistentMap<T, R>> = MutableStateFlow(persistentMapOf())

    override fun set(key: T, value: R) {
        cache.update { map ->
            map.put(key, value)
        }
    }

    override fun get(key: T): R? {
        return cache.value[key]
    }

    override fun getStateFlow(): StateFlow<ImmutableMap<T, R>> = cache.asStateFlow()

    override fun clear() {
        cache.value = persistentMapOf()
    }
}
