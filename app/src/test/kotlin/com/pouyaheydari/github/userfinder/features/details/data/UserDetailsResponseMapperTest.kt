package com.pouyaheydari.github.userfinder.features.details.data

import com.pouyaheydari.github.userfinder.features.details.data.mappers.map
import com.pouyaheydari.github.userfinder.features.details.data.models.UserDetailsApiResponse
import com.pouyaheydari.github.userfinder.features.details.data.models.UserDetailsDataState
import com.pouyaheydari.github.userfinder.features.details.data.models.UserDetailsModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class UserDetailsResponseMapperTest {

    @Test
    fun `when response is success then map the response to UserDetailsModel`() {

        val expected = UserDetailsDataState.Success(
            UserDetailsModel(repositories = 10, followers = 10)
        )
        val response = Result.success(getUserDetailsApiResponse())

        val result = map(response)

        assertTrue(result is UserDetailsDataState.Success)
        assertEquals(result, expected)
    }

    @Test
    fun `when result is failure then return failure`() {
        val response = Result.failure<UserDetailsApiResponse>(Exception())

        val result = map(response)

        assertTrue(result is UserDetailsDataState.Failure)
    }

    private fun getUserDetailsApiResponse() = UserDetailsApiResponse(
        avatarUrl = null,
        bio = null,
        blog = null,
        company = null,
        createdAt = null,
        email = null,
        eventsUrl = null,
        followers = 10,
        followersUrl = null,
        following = 0,
        followingUrl = null,
        gistsUrl = null,
        gravatarId = null,
        hireable = null,
        htmlUrl = null,
        id = 0,
        location = null,
        login = "",
        name = null,
        nodeId = null,
        organizationsUrl = null,
        publicGists = null,
        publicRepos = 10,
        receivedEventsUrl = null,
        reposUrl = null,
        siteAdmin = null,
        starredUrl = null,
        subscriptionsUrl = null,
        twitterUsername = null,
        type = null,
        updatedAt = null,
        url = null
    )
}
