package com.pouyaheydari.github.userfinder.features.search.domain

import com.pouyaheydari.github.userfinder.data.UserRepository
import com.pouyaheydari.github.userfinder.features.search.data.models.SearchUsersDataState
import com.pouyaheydari.github.userfinder.features.search.data.models.UserModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.internal.verification.Times
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class SearchUsersUseCaseTest {

    private val userRepository: UserRepository = mock()
    private val searchUsersUseCase = SearchUsersUseCase(userRepository)

    @Test
    fun `when repository returns failure then usecase returns failure`() = runBlocking {

        val param = "test"
        whenever(userRepository.searchUsers(param, 1, 100))
            .thenReturn(SearchUsersDataState.Failure)

        val result = searchUsersUseCase(param)

        assertTrue(result is SearchUsersDataState.Failure)
    }

    @Test
    fun `when repository returns failure on a page then usecase requests same page on the next call`(): Unit =
        runBlocking {

            val param = "test"
            whenever(userRepository.searchUsers(param, 1, 100))
                .thenReturn(SearchUsersDataState.Failure)

            searchUsersUseCase(param) // second call
            searchUsersUseCase(param) // first call

            verify(userRepository, Times(2)).searchUsers(param, 1, 100)
        }

    @Test
    fun `when repository returns success then usecase returns success`(): Unit =
        runBlocking {

            val param = "test"
            val userList = listOf(UserModel("url", "title"))
            val expected = SearchUsersDataState.Success(userList)
            whenever(userRepository.searchUsers(param, 1, 100)).thenReturn(expected)

            val result = searchUsersUseCase(param)

            assertTrue(result is SearchUsersDataState.Success)
            assertEquals(expected, result)

        }

    @Test
    fun `when repository returns success then usecase requests next page on the next call`(): Unit =
        runBlocking {

            val param = "test"
            val userList = listOf(UserModel("url", "title"))
            val expected = SearchUsersDataState.Success(userList)
            whenever(userRepository.searchUsers(param, 1, 100)).thenReturn(expected)
            whenever(userRepository.searchUsers(param, 2, 100)).thenReturn(expected)

            searchUsersUseCase(param)
            searchUsersUseCase(param)

            verify(userRepository, Times(1)).searchUsers(param, 1, 100)
            verify(userRepository, Times(1)).searchUsers(param, 2, 100)
        }

    @Test
    fun `when repository fetches next page then returns whole first and second page items`(): Unit =
        runBlocking {

            val param = "test"
            val firstItem = UserModel("url1", "title1")
            val secondItem = UserModel("url2", "title2")
            val expected = SearchUsersDataState.Success(listOf(firstItem, secondItem))
            whenever(userRepository.searchUsers(param, 1, 100))
                .thenReturn(SearchUsersDataState.Success(listOf(firstItem)))
            whenever(userRepository.searchUsers(param, 2, 100))
                .thenReturn(SearchUsersDataState.Success(listOf(secondItem)))

            searchUsersUseCase(param)
            val result = searchUsersUseCase(param)

            assertEquals(expected, result)
        }

    @Test
    fun `when the userName changes then usecase calls repository with correct parameters`(): Unit =
        runBlocking {

            val firstParam = "test"
            val secondParam = "test1"
            val firstResponse = listOf(UserModel("url", "title"))
            val secondResponse = listOf(UserModel("url1", "title1"))

            whenever(userRepository.searchUsers(firstParam, 1, 100))
                .thenReturn(SearchUsersDataState.Success(firstResponse))
            whenever(userRepository.searchUsers(secondParam, 1, 100))
                .thenReturn(SearchUsersDataState.Success(secondResponse))

            searchUsersUseCase(firstParam)
            searchUsersUseCase(secondParam)

            verify(userRepository, Times(1)).searchUsers(firstParam, 1, 100)
            verify(userRepository, Times(1)).searchUsers(secondParam, 1, 100)
        }

    @Test
    fun `when the userName changes then usecase returns the correct new response`(): Unit =
        runBlocking {

            val firstParam = "test"
            val secondParam = "test1"
            val firstResponse = listOf(UserModel("url", "title"))
            val secondResponse = listOf(UserModel("url1", "title1"))
            val expected = SearchUsersDataState.Success(secondResponse)

            whenever(userRepository.searchUsers(firstParam, 1, 100))
                .thenReturn(SearchUsersDataState.Success(firstResponse))
            whenever(userRepository.searchUsers(secondParam, 1, 100))
                .thenReturn(expected)

            searchUsersUseCase(firstParam)
            val result = searchUsersUseCase(secondParam)

            assertEquals(expected, result)
        }
}
