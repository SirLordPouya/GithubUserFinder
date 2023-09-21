package com.pouyaheydari.github.userfinder.features.details.data.mappers

import com.pouyaheydari.github.userfinder.features.details.data.models.UserDetailsApiResponse
import com.pouyaheydari.github.userfinder.features.details.data.models.UserDetailsDataState
import com.pouyaheydari.github.userfinder.features.details.data.models.UserDetailsModel

fun map(response: Result<UserDetailsApiResponse>): UserDetailsDataState =
    when {
        response.isSuccess -> handleSuccess(response)

        else -> handleFailure()
    }

private fun handleFailure(): UserDetailsDataState = UserDetailsDataState.Failure

private fun handleSuccess(response: Result<UserDetailsApiResponse>): UserDetailsDataState =
    response.getOrNull()?.let {
        return UserDetailsDataState.Success(
            UserDetailsModel(repositories = it.publicRepos, followers = it.followers)
        )
    } ?: handleFailure()