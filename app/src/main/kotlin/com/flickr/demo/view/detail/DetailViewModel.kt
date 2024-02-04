package com.flickr.demo.view.detail

import androidx.lifecycle.ViewModel
import com.flickr.demo.common.data.PhotoItem
import com.flickr.demo.common.data.PhotosRepository
import com.flickr.demo.common.scalars.Url
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val photosRepository: PhotosRepository,
) : ViewModel() {
    fun photoStateFlow(url: Url): PhotoItem? = photosRepository.getPhotoByUrl(url)
}
