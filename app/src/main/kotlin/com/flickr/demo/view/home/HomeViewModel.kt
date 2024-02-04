package com.flickr.demo.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val photosRepository: PhotosRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val retryEvents: MutableSharedFlow<Unit> = MutableSharedFlow()
    private val errorsChannel: Channel<Unit> = Channel()
    val errorsFlow: Flow<Unit> = errorsChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            combine(
                uiState.map { it.searchTags }.distinctUntilChanged(),
                flow {
                    // initial event is needed to start combine
                    emit(Unit)
                    // retry events from UI
                    emitAll(retryEvents)
                },
            ) { tags, _ -> tags }.collectLatest { tags ->
                try {
                    // `transformLatest()` and `delay()` is used instead of `debounce()` so that an
                    // in flight request is cancelled immediately if the tags have changed.
                    delay(typingDebounce.inWholeMilliseconds)
                    _uiState.update { uiState ->
                        uiState.copy(loading = true)
                    }
                    val photos = photosRepository.getPhotosByTags(tags)
                    _uiState.update { uiState ->
                        uiState.copy(photos = photos)
                    }
                } catch (throwable: Throwable) {
                    currentCoroutineContext().ensureActive()
                    errorsChannel.send(Unit)
                } finally {
                    _uiState.update { uiState ->
                        uiState.copy(loading = false)
                    }
                }
            }
        }
    }

    fun searchTagsChange(tags: Tags) {
        _uiState.update { uiState ->
            uiState.copy(searchTags = tags)
        }
    }

    suspend fun retry() {
        retryEvents.emit(Unit)
    }

    companion object {
        val typingDebounce = 1.seconds
    }
}
