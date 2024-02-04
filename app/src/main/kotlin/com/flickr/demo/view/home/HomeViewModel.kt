package com.flickr.demo.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flickr.demo.common.data.PhotoItem
import com.flickr.demo.common.data.PhotosRepository
import com.flickr.demo.common.scalars.Tags
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.transformLatest
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val photosRepository: PhotosRepository,
) : ViewModel() {
    val searchTagsStateFlow: MutableStateFlow<Tags> = MutableStateFlow(Tags(""))
    private val retryEvent: MutableSharedFlow<Unit> = MutableSharedFlow()
    private val loadingMutableStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loadingStateFlow = loadingMutableStateFlow.asStateFlow()
    private val errorsChannel: Channel<Unit> = Channel()
    val errorsFlow: Flow<Unit> = errorsChannel.receiveAsFlow()

    val photosStateFlow: SharedFlow<List<PhotoItem>> =
        combine(
            searchTagsStateFlow,
            flow {
                // initial event is needed to start combine
                emit(Unit)
                // retry events from UI
                emitAll(retryEvent)
            },
        ) { tags, _ -> tags }
            .transformLatest { tags ->
                try {
                    // `transformLatest()` and `delay()` is used instead of `debounce()` so that an
                    // in flight request is cancelled immediately if the tags have changed.
                    delay(typingDebounce.inWholeMilliseconds)
                    loadingMutableStateFlow.value = true
                    emit(photosRepository.getPhotosByTags(tags))
                } catch (throwable: Throwable) {
                    currentCoroutineContext().ensureActive()
                    errorsChannel.send(Unit)
                } finally {
                    loadingMutableStateFlow.value = false
                }
            }
            .shareIn(
                scope = viewModelScope,
                // Share results with timeout so that config changes don't cancel this flow.
                started = SharingStarted.WhileSubscribed(subscriptionTimeout.inWholeMilliseconds),
                // Ensure that new subscribers get latest value.
                replay = 1,
            )

    suspend fun retry() {
        retryEvent.emit(Unit)
    }

    companion object {
        val typingDebounce = 1.seconds
        val subscriptionTimeout = 2.seconds
    }
}
