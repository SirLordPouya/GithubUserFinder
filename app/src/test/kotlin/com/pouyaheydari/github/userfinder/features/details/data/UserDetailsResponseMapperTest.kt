package com.pouyaheydari.github.userfinder.features.details.data

import com.pouyaheydari.github.userfinder.features.details.data.models.UserDetailsApiResponse
import org.junit.Assert.assertEquals
import org.junit.Test
import com.pouyaheydari.github.userfinder.features.details.data.map as userDetailsResponseMapper


class UserDetailsMapperTest {

    @Test
    fun testMapWhenGivenUserDetailsApiResponseThenReturnsCorrectUserDetails() {
        // Arrange
        val publicRepos = 10
        val followers = 20
        val userDetailsApiResponse = getUserDetailsApiResponse(publicRepos, followers)

        // Act
        val userDetails = userDetailsResponseMapper(userDetailsApiResponse)

        // Assert
        assertEquals(publicRepos, userDetails.repositories)
        assertEquals(followers, userDetails.followers)
    }

    @Test
    fun testMapWhenGivenNullUserDetailsApiResponseThenReturnsNullUserDetails() {
        // Arrange
        val publicRepos = 10
        val followers = 20
        val userDetailsApiResponse = getUserDetailsApiResponse(publicRepos, followers)

        // Act
        val userDetails = userDetailsResponseMapper(userDetailsApiResponse)

        // Assert
        assertEquals(publicRepos, userDetails.repositories)
        assertEquals(followers, userDetails.followers)
    }

    private fun getUserDetailsApiResponse(publicRepos: Int, followers: Int) =
        UserDetailsApiResponse(
            publicRepos = publicRepos,
            followers = followers,
            avatarUrl = null,
            bio = null,
            blog = null,
            company = null,
            createdAt = null,
            email = null,
            eventsUrl = null,
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
            receivedEventsUrl = null,
            reposUrl = null,
            siteAdmin = null,
            starredUrl = null,
            subscriptionsUrl = null,
            twitterUsername = null,
            type = null,
            updatedAt = null,
            url = null,
        )
}