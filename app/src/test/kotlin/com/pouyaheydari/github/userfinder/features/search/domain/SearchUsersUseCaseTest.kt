package com.pouyaheydari.github.userfinder.features.search.domain

import com.pouyaheydari.github.userfinder.data.UserRepository
import com.pouyaheydari.github.userfinder.features.search.data.models.UserModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchUsersUseCaseTest {

    private val userRepository: UserRepository = mock(UserRepository::class.java)

    private val searchUsersUseCase = SearchUsersUseCase(userRepository)

    @Test
    fun testInvokeWhenValidPhraseThenCallsSearchUsersWithCorrectParameters(): Unit = runBlocking {
        // Arrange
        val phrase = "test"
        val expectedUsers = listOf<UserModel>()
        Mockito.`when`(userRepository.searchUsers(phrase, 1)).thenReturn(expectedUsers)

        // Act
        searchUsersUseCase.invoke(phrase)

        // Assert
        Mockito.verify(userRepository).searchUsers(phrase, 1)
    }

    @Test
    fun testInvokeWhenSearchUsersReturnsResultThenInvokeReturnsSameResult(): Unit = runBlocking {
        // Arrange
        val phrase = "test"
        val expectedUsers = listOf<UserModel>()
        Mockito.`when`(userRepository.searchUsers(phrase, 1)).thenReturn(expectedUsers)

        // Act
        val result = searchUsersUseCase.invoke(phrase)

        // Assert
        assertEquals(expectedUsers, result)
    }
}