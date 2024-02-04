package com.flickr.demo.view.home

import com.flickr.demo.common.data.PhotoItem
import com.flickr.demo.common.data.PhotosRepository
import com.flickr.demo.common.scalars.Tags
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContentEquals

class HomeViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private val photosRepositoryMock: PhotosRepository = mockk()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `Load Success Test`() = runTest(testDispatcher) {
        val tags = Tags("")
        val photos = listOf<PhotoItem>(mockk())

        coEvery { photosRepositoryMock.getPhotosByTags(any()) } coAnswers {
            delay(1_000)
            photos
        }

        HomeViewModel(photosRepositoryMock).apply {
            val uiStates = async {
                uiState.take(3).toList()
            }

            assertContentEquals(
                expected = listOf(
                    // Order of expected uiState changes
                    HomeUiState(searchTags = tags, loading = false, photos = null),
                    HomeUiState(searchTags = tags, loading = true, photos = null),
                    HomeUiState(searchTags = tags, loading = false, photos = photos),
                ),
                actual = uiStates.await(),
            )
        }

        coVerify {
            photosRepositoryMock.getPhotosByTags(tags)
        }
    }

    @Test
    fun `Load Failure Test`() = runTest(testDispatcher) {
        val tags = Tags("")

        coEvery { photosRepositoryMock.getPhotosByTags(any()) } coAnswers {
            delay(1_000)
            throw IllegalStateException("test")
        }

        HomeViewModel(photosRepositoryMock).apply {
            val uiStates = async {
                uiState.take(3).toList()
            }

            val errors = async {
                errorsFlow.take(1).toList()
            }

            assertContentEquals(
                expected = listOf(
                    // Expected final UI state
                    HomeUiState(searchTags = tags, loading = false, photos = null),
                    HomeUiState(searchTags = tags, loading = true, photos = null),
                    HomeUiState(searchTags = tags, loading = false, photos = null),
                ),
                actual = uiStates.await(),
            )

            // Verify error event emitted
            assertContentEquals(
                expected = listOf(
                    Unit,
                ),
                actual = errors.await(),
            )
        }

        coVerify { photosRepositoryMock.getPhotosByTags(tags) }
    }
}
