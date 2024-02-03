package com.flickr.demo.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flickr.demo.common.data.PhotoItem
import com.flickr.demo.common.data.PhotosRepository
import com.flickr.demo.common.scalars.Tags
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val photosRepository: PhotosRepository,
) : ViewModel() {
    val searchTagsStateFlow: MutableStateFlow<Tags> = MutableStateFlow(Tags(""))
    private val loadingMutableStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loadingStateFlow = loadingMutableStateFlow.asStateFlow()
    private val errorsStateFlow: Channel<Throwable?> = Channel()

    val photosStateFlow: SharedFlow<List<PhotoItem>> = searchTagsStateFlow
        .mapLatest { tags ->
            // `mapLatest()` and `delay()` is used instead of `debounce()` so that a request is
            // cancelled immediately if the user starts typing when a previous request is in flight.
            delay(1_000)
            try {
                loadingMutableStateFlow.value = true
                photosRepository.getPhotosByTags(tags)
            } finally {
                loadingMutableStateFlow.value = false
            }
        }
        .catch { throwable -> errorsStateFlow.send(throwable) }
        .shareIn(
            scope = viewModelScope,
            // Share results with timeout so that config changes don't cancel this flow.
            started = SharingStarted.WhileSubscribed(2_000),
            // Ensure that new subscribers get latest value.
            replay = 1,
        )
}
