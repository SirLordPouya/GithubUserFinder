package com.pouyaheydari.github.userfinder.features.search.data.mappers

import com.pouyaheydari.github.userfinder.features.search.data.models.Item
import com.pouyaheydari.github.userfinder.features.search.data.models.SearchUsersApiResponse
import com.pouyaheydari.github.userfinder.features.search.data.models.SearchUsersDataState
import com.pouyaheydari.github.userfinder.features.search.data.models.UserModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SearchUserResponseMapperTest {

    @Test
    fun `when response is success and list is empty then map the response to empty list of UserModel`() {

        val expected = SearchUsersDataState.Success(emptyList())
        val response = Result.success(getSearchUsersApiResponse(emptyList()))

        val result = map(response)

        assertTrue(result is SearchUsersDataState.Success)
        assertEquals(result, expected)
    }

    @Test
    fun `when response is success and list is not empty then map the response to list of UserModel`() {

        val imageUrl = "url"
        val userName = "userName"
        val expected = SearchUsersDataState.Success(listOf(UserModel(imageUrl, userName)))
        val response = Result.success(
            getSearchUsersApiResponse(
                listOf(
                    Item(
                        avatarUrl = imageUrl,
                        eventsUrl = "",
                        followersUrl = "",
                        followingUrl = "",
                        gistsUrl = "",
                        gravatarId = "",
                        htmlUrl = "",
                        id = 0,
                        login = userName,
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
            )
        )

        val result = map(response)

        assertTrue(result is SearchUsersDataState.Success)
        assertEquals(result, expected)
    }

    @Test
    fun `when result is failure then return failure`() {
        val response = Result.failure<SearchUsersApiResponse>(Exception())

        val result = map(response)

        assertTrue(result is SearchUsersDataState.Failure)
    }

    private fun getSearchUsersApiResponse(items: List<Item>) = SearchUsersApiResponse(
        incompleteResults = false,
        items = items,
        totalCount = 0
    )
}
