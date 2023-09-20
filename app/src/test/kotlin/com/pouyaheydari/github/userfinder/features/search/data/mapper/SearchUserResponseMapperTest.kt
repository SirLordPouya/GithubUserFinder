package com.pouyaheydari.github.userfinder.features.search.data.mapper

import com.pouyaheydari.github.userfinder.features.search.data.models.Item
import com.pouyaheydari.github.userfinder.features.search.data.models.SearchUsersApiResponse
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.pouyaheydari.github.userfinder.features.search.data.mapper.map as searchUserResponseMapper

@RunWith(JUnit4::class)
class SearchUserResponseMapperTest {

    @Test
    fun testMapWhenGivenSearchUsersApiResponseThenReturnsListOfUserModels() {
        // Arrange
        val response =
            SearchUsersApiResponse(incompleteResults = false, items = getItems(), totalCount = 2)

        // Act
        val result = searchUserResponseMapper(response)

        // Assert
        assertEquals(2, result.size)
        assertEquals("url1", result[0].imageUrl)
        assertEquals("login1", result[0].title)
        assertEquals("url2", result[1].imageUrl)
        assertEquals("login2", result[1].title)
    }

    @Test
    fun testMapWhenGivenEmptySearchUsersApiResponseThenReturnsEmptyList() {
        // Arrange
        val response =
            SearchUsersApiResponse(incompleteResults = false, items = emptyList(), totalCount = 0)

        // Act
        val result = searchUserResponseMapper(response)

        // Assert
        assertEquals(0, result.size)
    }

    private fun getItems() = listOf(
        Item(
            avatarUrl = "url1",
            login = "login1",
            eventsUrl = "",
            followersUrl = "",
            followingUrl = "",
            gistsUrl = "",
            gravatarId = "",
            htmlUrl = "",
            id = 0,
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
        ),
        Item(
            avatarUrl = "url2",
            login = "login2",
            eventsUrl = "",
            followersUrl = "",
            followingUrl = "",
            gistsUrl = "",
            gravatarId = "",
            htmlUrl = "",
            id = 0,
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
    )
}