package com.pouyaheydari.github.userfinder.data

import com.pouyaheydari.github.userfinder.features.search.data.UsersApiService
import com.pouyaheydari.github.userfinder.features.search.data.models.SearchUsersApiResponse
import com.pouyaheydari.github.userfinder.features.search.data.models.SearchUsersDataState
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever

class UserRepositoryImplTest {

    private val usersApiService: UsersApiService = mock()
    private val userRepository = UserRepositoryImpl(usersApiService)

    @Test
    fun `when service returns data then map the response to success`() = runBlocking {
        val userName = "test"
        val page = 1
        val itemsPerPage = 10
        val expectedData = SearchUsersDataState.Success(emptyList())
        whenever(usersApiService.searchUsers(userName, page, itemsPerPage)).thenReturn(
            Result.success(
                SearchUsersApiResponse(incompleteResults = false, items = listOf(), totalCount = 0)
            )
        )

        val result = userRepository.searchUsers(userName, page, itemsPerPage)

        verify(usersApiService).searchUsers(userName, page, itemsPerPage)
        assertEquals(expectedData, result)
    }

    @Test
    fun `when service returns error then map the response to failure`(): Unit = runBlocking {
        val userName = ""
        val page = -1
        val itemsPerPage = 0
        val expected = SearchUsersDataState.Failure
        whenever(usersApiService.searchUsers(userName, page, itemsPerPage))
            .thenReturn(
                Result.failure(Exception())
            )

        val result = userRepository.searchUsers(userName, page, itemsPerPage)

        assertEquals(expected, result)
    }
}
