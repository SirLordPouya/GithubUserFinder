package com.pouyaheydari.github.userfinder.data

import com.pouyaheydari.github.userfinder.features.details.data.models.UserDetails
import com.pouyaheydari.github.userfinder.features.details.data.models.UserDetailsApiResponse
import com.pouyaheydari.github.userfinder.features.search.data.UsersApiService
import com.pouyaheydari.github.userfinder.features.search.data.models.Item
import com.pouyaheydari.github.userfinder.features.search.data.models.SearchUsersApiResponse
import com.pouyaheydari.github.userfinder.features.search.data.models.UserModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserRepositoryImplTest {

    private val usersApiService: UsersApiService = mock()

    private val userRepositoryImpl = UserRepositoryImpl(usersApiService)

    @Test
    fun testGetUserWhenCalledThenCallsApiServiceAndMapsResponse() = runBlocking {
        // Arrange
        val userName = "test"
        val expected = UserDetails(repositories = 10, followers = 20)
        `when`(usersApiService.getUser(userName))
            .thenReturn(getUserDetailsApiResponse(userName))

        // Act
        val result = userRepositoryImpl.getUser(userName)

        // Assert
        verify(usersApiService).getUser(userName)
        assertEquals(expected, result)
    }

    @Test(expected = Exception::class)
    fun testGetUserWhenExceptionThrownThenHandlesException(): Unit = runBlocking {
        // Arrange
        val userName = "test"
        `when`(usersApiService.getUser(userName)).thenThrow(Exception())

        // Act
        userRepositoryImpl.getUser(userName)

        // Assert is handled by the 'expected' parameter in the @Test annotation
    }

    @Test
    fun testSearchUsersWhenCalledThenCallsApiServiceAndReturnsCorrectResult() = runBlocking {
        // Arrange
        val phrase = "test"
        val page = 1
        val imageUrl = "url"
        val title = "title"
        val expected = listOf(UserModel(imageUrl, title))
        `when`(usersApiService.searchUsers(phrase, page))
            .thenReturn(getSearchUsersApiResponse(imageUrl, title))

        // Act
        val result = userRepositoryImpl.searchUsers(phrase, page)

        // Assert
        verify(usersApiService).searchUsers(phrase, page)
        assertEquals(expected, result)
    }

    private fun getSearchUsersApiResponse(imageUrl: String, title: String) = SearchUsersApiResponse(
        incompleteResults = false,
        items = listOf(
            Item(
                avatarUrl = imageUrl,
                eventsUrl = "",
                followersUrl = "",
                followingUrl = "",
                gistsUrl = "",
                gravatarId = "",
                htmlUrl = "",
                id = 0,
                login = title,
                nodeId = "",
                organizationsUrl = "",
                receivedEventsUrl = "",
                reposUrl = "",
                score = 0,
                siteAdmin = false,
                starredUrl = "",
                subscriptionsUrl = "",
                type = "",
                url = ""
            )
        ),
        totalCount = 0
    )

    private fun getUserDetailsApiResponse(userName: String) = UserDetailsApiResponse(
        login = userName,
        id = 0,
        nodeId = "",
        avatarUrl = "",
        gravatarId = "",
        url = "",
        htmlUrl = "",
        followersUrl = "",
        followingUrl = "",
        gistsUrl = "",
        starredUrl = "",
        subscriptionsUrl = "",
        organizationsUrl = "",
        reposUrl = "",
        eventsUrl = "",
        receivedEventsUrl = "",
        type = "",
        siteAdmin = false,
        name = "",
        company = null,
        blog = "",
        location = null,
        email = null,
        hireable = null,
        bio = null,
        twitterUsername = null,
        publicRepos = 10,
        publicGists = 0,
        followers = 20,
        following = 0,
        createdAt = "",
        updatedAt = ""
    )
}