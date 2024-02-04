package com.flickr.demo.view.home

import com.flickr.demo.common.data.PhotosRepository
import com.flickr.demo.common.scalars.Tags
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class HomeViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private val photosRepositoryMock: PhotosRepository = mockk()

    @Test
    fun `Load Success Test`() = runTest(testDispatcher) {
        coEvery { photosRepositoryMock.getPhotosByTags(any()) } returns emptyList()

        HomeViewModel(photosRepositoryMock).apply {
            val photoStates = async {
                photosStateFlow.take(1).toList()
            }

            searchTagsStateFlow.value = Tags("test")
        }
    }
}
