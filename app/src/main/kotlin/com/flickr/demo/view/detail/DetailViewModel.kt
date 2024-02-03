package com.flickr.demo.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flickr.demo.common.data.PhotoItem
import com.flickr.demo.common.data.PhotosRepository
import com.flickr.demo.common.scalars.Url
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val photosRepository: PhotosRepository,
) : ViewModel() {
    private val errorsStateFlow: Channel<Throwable?> = Channel()

    fun photoStateFlow(url: Url): SharedFlow<PhotoItem> = flow {
        emit(photosRepository.getPhotoByUrl(url)!!)
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
