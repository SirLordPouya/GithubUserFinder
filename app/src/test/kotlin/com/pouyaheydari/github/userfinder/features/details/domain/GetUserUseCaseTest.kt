package com.pouyaheydari.github.userfinder.features.details.domain

import com.pouyaheydari.github.userfinder.data.UserRepository
import com.pouyaheydari.github.userfinder.features.details.data.models.UserDetailsDataState
import com.pouyaheydari.github.userfinder.features.details.data.models.UserDetailsModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class GetUserUseCaseTest {

    private val userRepository: UserRepository = mock()
    private val useCase = GetUserUseCase(userRepository)

    @Test
    fun `when usecase gets called then repository is being called`(): Unit =
        runBlocking {
            val userName = "testUser"

            useCase(userName)

            verify(userRepository).getUser(userName)
        }

    @Test
    fun `when usecase is called then response is returned`() = runBlocking {

        val userName = "testUser"
        val expectedUserDetailsDataState =
            UserDetailsDataState.Success(UserDetailsModel(10, 10))
        whenever(userRepository.getUser(userName)).thenReturn(expectedUserDetailsDataState)

        val result = useCase(userName)

        assertEquals(expectedUserDetailsDataState, result)
    }
}
