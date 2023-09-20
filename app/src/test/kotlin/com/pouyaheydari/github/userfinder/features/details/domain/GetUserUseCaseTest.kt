package com.pouyaheydari.github.userfinder.features.details.domain

import com.pouyaheydari.github.userfinder.data.UserRepository
import com.pouyaheydari.github.userfinder.features.details.data.models.UserDetails
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class GetUserUseCaseTest {

    private val userRepository: UserRepository = mock()
    private val getUserUseCase = GetUserUseCase(userRepository)

    @Test
    fun testInvokeWhenCalledThenCallsGetUserOnUserRepository(): Unit = runBlocking {
        // Arrange
        val userName = "testUser"
        val userDetails = UserDetails(10, 20)
        Mockito.`when`(userRepository.getUser(userName)).thenReturn(userDetails)

        // Act
        getUserUseCase.invoke(userName)

        // Assert
        Mockito.verify(userRepository).getUser(userName)
    }

    @Test
    fun testInvokeWhenCalledThenReturnsCorrectUserDetails() = runBlocking {
        // Arrange
        val userName = "testUser"
        val userDetails = UserDetails(10, 20)
        Mockito.`when`(userRepository.getUser(userName)).thenReturn(userDetails)

        // Act
        val result = getUserUseCase.invoke(userName)

        // Assert
        assertEquals(userDetails, result)
    }
}