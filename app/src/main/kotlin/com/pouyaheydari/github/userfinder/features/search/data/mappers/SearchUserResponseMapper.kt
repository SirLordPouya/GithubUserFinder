package com.pouyaheydari.github.userfinder.features.search.data.mappers

import com.pouyaheydari.github.userfinder.features.search.data.models.SearchUsersApiResponse
import com.pouyaheydari.github.userfinder.features.search.data.models.SearchUsersDataState
import com.pouyaheydari.github.userfinder.features.search.data.models.UserModel

fun map(response: Result<SearchUsersApiResponse>): SearchUsersDataState =
    when {
        response.isSuccess -> handleSuccess(response.getOrNull())

        else -> handleFailure(response.exceptionOrNull())
    }

private fun handleFailure(exception: Throwable? = null): SearchUsersDataState {
    exception?.printStackTrace()
    return SearchUsersDataState.Failure
}

private fun handleSuccess(response: SearchUsersApiResponse?): SearchUsersDataState =
    response?.items?.let { list ->
        val mappedList = list.map {
            UserModel(imageUrl = it.avatarUrl, title = it.login)
        }
        SearchUsersDataState.Success(mappedList)
    } ?: handleFailure()
