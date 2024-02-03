package com.flickr.demo.view.home

import com.flickr.demo.common.data.PhotosRepository
import com.flickr.demo.common.scalars.Tags
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class HomeViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private val employeeDirectoryRepositoryMock: PhotosRepository = mockk()

    @Test
    fun `Load Success Test`() = runTest(testDispatcher) {
        coEvery { employeeDirectoryRepositoryMock.getPhotosByTags(Tags("")) } returns emptyList()
        coEvery { employeeDirectoryRepositoryMock.getPhotosByTags() } returns employees

//        HomeViewModel(employeeDirectoryRepositoryMock).test(this) {
//            expectInitialState()
//            containerHost.load()
//            expectState { copy(loading = true) }
//            expectState { copy(employeeDirectory = employees) }
//            expectState { copy(loading = false) }
//        }

        verify { employeeDirectoryRepositoryMock.getPhotosByTags() }
        coVerify { employeeDirectoryRepositoryMock.getPhotosByTags() }
    }

    @Test
    fun `Load Error Test`() = runTest(testDispatcher) {
        every { employeeDirectoryRepositoryMock.getPhotosByTags() } returns emptyList()
        coEvery { employeeDirectoryRepositoryMock.getPhotosByTags() } throws
                IllegalStateException("test")

//        HomeViewModel(employeeDirectoryRepositoryMock).test(this) {
//            expectInitialState()
//            containerHost.load()
//            expectState { copy(loading = true) }
//            expectSideEffect(HomeSideEffect.LoadingError)
//            expectState { copy(loading = false) }
//        }

        verify { employeeDirectoryRepositoryMock.getPhotosByTags() }
        coVerify { employeeDirectoryRepositoryMock.getPhotosByTags() }
    }

    @Test
    fun `Refresh Success Test`() = runTest(testDispatcher) {
        coEvery { employeeDirectoryRepositoryMock.getPhotosByTags() } returns employeesUpdate

//        HomeViewModel(employeeDirectoryRepositoryMock).test(
//            testScope = this,
//            initialState = HomeUiState(photos = employees),
//        ) {
//            expectInitialState()
//            containerHost.refresh()
//            expectState { copy(refreshing = true) }
//            expectState { copy(photos = employeesUpdate) }
//            expectState { copy(refreshing = false) }
//        }

        coVerify { employeeDirectoryRepositoryMock.getPhotosByTags() }
    }

    @Test
    fun `Refresh Error Test`() = runTest(testDispatcher) {
        coEvery { employeeDirectoryRepositoryMock.getPhotosByTags() } throws
                IllegalStateException("test")

//        HomeViewModel(employeeDirectoryRepositoryMock).test(
//            testScope = this,
//            initialState = HomeUiState(photos = employees),
//        ) {
//            expectInitialState()
//            containerHost.refresh()
//            expectState { copy(refreshing = true) }
//            expectSideEffect(HomeSideEffect.RefreshError)
//            expectState { copy(refreshing = false) }
//        }

        coVerify { employeeDirectoryRepositoryMock.getPhotosByTags() }
    }
}
