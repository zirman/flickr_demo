package com.flickr.demo.view.home

import com.flickr.demo.common.data.PhotoItem
import com.flickr.demo.common.data.PhotosRepository
import com.flickr.demo.common.scalars.Tags
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
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

    @AfterTest
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Load Success Test`() = runTest {
        val tags = Tags("")
        val photos = listOf<PhotoItem>(mockk())

        coEvery { photosRepositoryMock.getPhotosByTags(any()) } coAnswers {
            delay(1_000)
            photos
        }

        HomeViewModel(photosRepositoryMock).apply {
            val uiStates = mutableListOf<HomeUiState>()

            backgroundScope.launch(UnconfinedTestDispatcher()) {
                uiState.toList(uiStates)
            }

            advanceUntilIdle()

            assertContentEquals(
                expected = listOf(
                    HomeUiState(searchTags = tags, loading = false, photos = null),
                    HomeUiState(searchTags = tags, loading = true, photos = null),
                    HomeUiState(searchTags = tags, loading = true, photos = photos),
                    HomeUiState(searchTags = tags, loading = false, photos = photos),
                ),
                actual = uiStates,
            )
        }

        coVerify {
            photosRepositoryMock.getPhotosByTags(tags)
        }
    }

    @Test
    fun `Load Failure Test`() = runTest {
        val tags = Tags("")

        coEvery { photosRepositoryMock.getPhotosByTags(any()) } coAnswers {
            delay(1_000)
            throw IllegalStateException("test")
        }

        HomeViewModel(photosRepositoryMock).apply {
            val uiStates = mutableListOf<HomeUiState>()
            val errors = mutableListOf<Unit>()

            backgroundScope.launch(UnconfinedTestDispatcher()) {
                uiState.toList(uiStates)
            }

            backgroundScope.launch(UnconfinedTestDispatcher()) {
                errorsFlow.toList(errors)
            }

            advanceUntilIdle()

            assertContentEquals(
                expected = listOf(
                    HomeUiState(searchTags = tags, loading = false, photos = null),
                    HomeUiState(searchTags = tags, loading = true, photos = null),
                    HomeUiState(searchTags = tags, loading = false, photos = null),
                ),
                actual = uiStates,
            )

            // Verify error event emitted
            assertContentEquals(
                expected = listOf(Unit),
                actual = errors,
            )
        }

        coVerify { photosRepositoryMock.getPhotosByTags(tags) }
    }
}
